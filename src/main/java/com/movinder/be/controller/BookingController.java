package com.movinder.be.controller;

import com.movinder.be.entity.Cinema;
import com.movinder.be.entity.Food;
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




}
