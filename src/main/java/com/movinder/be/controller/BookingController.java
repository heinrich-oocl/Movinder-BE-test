package com.movinder.be.controller;

import com.movinder.be.controller.dto.BookingRequest;
import com.movinder.be.entity.Booking;
import com.movinder.be.entity.Food;
import com.movinder.be.entity.Ticket;
import com.movinder.be.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_PAGE_SIZE = "20";

    public BookingController (BookingService bookingService){
        this.bookingService = bookingService;
    }

    /*
    Food
     */
    @GetMapping("/food")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Food> getFood(@RequestParam(defaultValue = "") String foodName,
                              @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize){
        return bookingService.getFood(foodName, page, pageSize);
    }

    @GetMapping("/food/{foodID}")
    @ResponseStatus(code = HttpStatus.OK)
    public Food getFoodById(@PathVariable String foodID){

        return bookingService.findFoodById(foodID);
    }

    @PostMapping("/food")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Food addFood(@RequestBody Food food){
        return bookingService.createFood(food);
    }


    /*
    Ticket
     */
    @GetMapping("/ticket/{ticketID}")
    @ResponseStatus(code = HttpStatus.OK)
    public Ticket getTicketById(@PathVariable String ticketID){
        return bookingService.findTicketById(ticketID);
    }


    /*
    Booking
     */
    @PostMapping("/order")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Booking createBooking(@RequestBody BookingRequest bookingRequest){
        return bookingService.createBooking(bookingRequest);
    }

    @GetMapping("/order/{customerID}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Booking> getOrders(@PathVariable String customerID,
                                    @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                    @RequestParam(required = false) String from,
                                    @RequestParam(required = false) String to,
                                    @RequestParam(required = false, defaultValue = "true") Boolean ascending){
        return bookingService.getBookingList(customerID, page, pageSize, from, to, ascending);
    }


}
