package com.movinder.be.service;

import com.movinder.be.controller.dto.BookingRequest;
import com.movinder.be.controller.dto.RequestItem;
import com.movinder.be.entity.*;
import com.movinder.be.exception.IdNotFoundException;
import com.movinder.be.exception.MalformedRequestException;
import com.movinder.be.exception.ProvidedKeyAlreadyExistException;
import com.movinder.be.exception.RequestDataNotCompleteException;
import com.movinder.be.repository.BookingRepository;
import com.movinder.be.repository.FoodRepository;
import com.movinder.be.repository.TicketRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingService {
    private final FoodRepository foodMongoRepository;
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private final MovieService movieService;
    private final CustomerService customerService;

    private static final int DEFAULT_BOOKING_SEARCH_PERIOD = 3;

    public BookingService(FoodRepository foodMongoRepository,
                          TicketRepository ticketRepository,
                          MovieService movieService,
                          BookingRepository bookingRepository,
                          CustomerService customerService) {
        this.bookingRepository = bookingRepository;
        this.foodMongoRepository = foodMongoRepository;
        this.ticketRepository = ticketRepository;
        this.movieService = movieService;
        this.customerService = customerService;
    }

    /*
    Food
     */

    public Food createFood(Food food) {
        if (food.getFoodId() != null) {
            throw new MalformedRequestException("Create food request should not contain ID");
        }
        validateFoodAttributes(food);
        try {
            return foodMongoRepository.save(food);
        } catch (DuplicateKeyException customerExist) {
            throw new ProvidedKeyAlreadyExistException("Food name");
        }
    }

    public List<Food> getFood(String foodName, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return foodMongoRepository.findByfoodNameIgnoringCaseContaining(foodName, pageable);
    }

    public Food findFoodById(String foodId) {
        Utility.validateID(foodId);
        return foodMongoRepository.findById(foodId).orElseThrow(() -> new IdNotFoundException("Food"));
    }


    /*
    Ticket
     */

    public Ticket bookTicket(String ticketType, Integer price, Seat seat){
        return ticketRepository.save(new Ticket(ticketType, price, seat));
    }

    public Ticket findTicketById(String ticketId) {
        Utility.validateID(ticketId);
        return ticketRepository
                .findById(ticketId)
                .orElseThrow(() -> new IdNotFoundException("Ticket"));
    }


    /*
    Booking
     */

    //create Booking
    public Booking createBooking(BookingRequest bookingRequest) {
        /*
        1. check seat num match and available
        2. check food id exist
        3. check booking type exist
        4. occupy avaialble seatings
        5. create tickets
        6. add food ids to food_ids
        7. save Booking and return
         */

        validateBookingRequestAttributes(bookingRequest);

        // validate customer id
        customerService.findByCustomerId(bookingRequest.getCustomerId());

        // check booking num match
        checkQuantityMatch(bookingRequest);

        // verify food exist
        ArrayList<Food> foodList = generateFoodList(bookingRequest.getFoodRequestItems());
        ArrayList<String> foodIds = foodList.stream().map(Food::getFoodId).collect(Collectors.toCollection(ArrayList::new));

        // check booking type exist
        Map<String, Integer> sessionPricing = getSessionPricing(movieService.findMovieSessionById(bookingRequest.getMovieSessionId()));
        checkTicketTypeExist(sessionPricing, bookingRequest.getTicketRequestItems());

        // use movie session to book seat
        MovieSession movieSession = movieService.bookSeats(bookingRequest.getMovieSessionId(), bookingRequest.getSeatingRequests());

        // pricingMap
        ArrayList<String> ticketIds = bookTickets(bookingRequest, sessionPricing);

        //book food
        Integer foodPrice = foodList.stream().mapToInt(Food::getPrice).sum();
        Integer ticketPrice = ticketIds.stream().mapToInt(ticketId -> findTicketById(ticketId).getPrice()).sum();

        return bookingRepository.save(
                new Booking(bookingRequest.getCustomerId(),
                        movieSession.getSessionId(),
                        ticketIds, foodIds,
                        foodPrice+ticketPrice));


    }

    private ArrayList<String> bookTickets(BookingRequest bookingRequest, Map<String, Integer> sessionPricing){
        // create tickets
        ArrayList<String> ticketIds = new ArrayList<>();
        ArrayList<Seat> seats = bookingRequest.getSeatingRequests();
        bookingRequest.getTicketRequestItems().forEach(requestItem -> {
            String ticketType = requestItem.getItem();
            for (int i=0; i< requestItem.getQuantity();i++) {
                // book a ticket
                Ticket ticket = bookTicket(ticketType, sessionPricing.get(ticketType), seats.remove(0));
                ticketIds.add(ticket.getTicketId());
            }
        });
        return ticketIds;
    }

    private Map<String, Integer> getSessionPricing(MovieSession movieSession){
        return movieSession.getPricing()
                .stream()
                .collect(Collectors.toMap(Pricing::getItem, Pricing::getPrice));
    }

    private void checkTicketTypeExist(Map<String, Integer> sessionPricing, ArrayList<RequestItem> ticketRequests){

        ticketRequests.forEach(requestItem -> {
            if (!sessionPricing.containsKey(requestItem.getItem())){
                throw new MalformedRequestException("ticketRequestItems contains fields not found in Movie session: "+ requestItem.getItem());
            }
        });

    }

    // Food item count are duplicated by quantity
    private ArrayList<Food> generateFoodList(ArrayList<RequestItem> foodRequests){
        ArrayList<Food> foodList = new ArrayList<>();
        foodRequests.forEach(foodRequest -> {
            Food food = findFoodById(foodRequest.getItem());
            for (int i = 0; i<foodRequest.getQuantity(); i++){
                foodList.add(food);
            }
        });
        return foodList;
    }

    private void checkQuantityMatch(BookingRequest bookingRequest) {
        int bookingQuantity = bookingRequest.getTicketRequestItems()
                .stream()
                .mapToInt(RequestItem::getQuantity)
                .sum();
        if (bookingQuantity != bookingRequest.getSeatingRequests().size()) {
            throw new MalformedRequestException("Booking quantity not match seatings requested");
        }
    }

    // todo: search past booking sort by time

    public List<Booking> getBookingList(String customerId, Integer page, Integer pageSize, String from, String to){

        Utility.validateID(customerId);

        LocalDateTime fromDate = from == null ? LocalDateTime.now().minusMonths(DEFAULT_BOOKING_SEARCH_PERIOD) : LocalDateTime.parse(from);
        LocalDateTime toDate = to == null ? LocalDateTime.now() : LocalDateTime.parse(to);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "bookingTime");

        return bookingRepository.findByCustomerIdAndBookingTimeBetween(customerId, fromDate, toDate, pageable);
    }





    /*
    Checking
     */


    // checks if object contains null attributes
    private void validateFoodAttributes(Food food) {

        boolean containsNull = Stream
                .of(food.getFoodName(), food.getPrice(), food.getDescription())
                .anyMatch(Objects::isNull);

        if (containsNull) {
            throw new RequestDataNotCompleteException("Food");
        }
    }

    private void validateBookingRequestAttributes(BookingRequest bookingRequest) {

        boolean containsNull = Stream
                .of(bookingRequest.getCustomerId(),
                        bookingRequest.getMovieSessionId(),
                        bookingRequest.getTicketRequestItems(),
                        bookingRequest.getFoodRequestItems(),
                        bookingRequest.getSeatingRequests())
                .anyMatch(Objects::isNull);

        if (containsNull) {
            throw new RequestDataNotCompleteException("Booking Request");
        }


        //todo find way to dedup code
        containsNull = bookingRequest
                .getTicketRequestItems()
                .parallelStream()
                .anyMatch(requestItem ->
                        Stream
                                .of(requestItem.getItem(), requestItem.getQuantity())
                                .anyMatch(Objects::isNull));
        if (containsNull) {
            throw new RequestDataNotCompleteException("Booking Request Item");
        }

        containsNull = bookingRequest
                .getFoodRequestItems()
                .parallelStream()
                .anyMatch(requestItem ->
                        Stream
                                .of(requestItem.getItem(), requestItem.getQuantity())
                                .anyMatch(Objects::isNull));
        if (containsNull) {
            throw new RequestDataNotCompleteException("Food Request Item");
        }

        containsNull = bookingRequest
                .getSeatingRequests()
                .parallelStream()
                .anyMatch(seatings ->
                        Stream.of(seatings.getRow(), seatings.getColumn()).anyMatch(Objects::isNull));
        if (containsNull) {
            throw new RequestDataNotCompleteException("Booking Request Seating");
        }
    }

}
