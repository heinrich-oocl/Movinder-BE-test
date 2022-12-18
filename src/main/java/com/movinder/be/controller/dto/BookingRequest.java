package com.movinder.be.controller.dto;

import com.movinder.be.entity.Seat;

import java.util.ArrayList;

public class BookingRequest {
    private String customerId;
    private String movieSessionId;
    private ArrayList<RequestItem> ticketRequestItems;
    private ArrayList<RequestItem> foodRequestItems;
    private ArrayList<Seat> seatingRequests;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMovieSessionId() {
        return movieSessionId;
    }

    public void setMovieSessionId(String movieSessionId) {
        this.movieSessionId = movieSessionId;
    }

    public ArrayList<RequestItem> getTicketRequestItems() {
        return ticketRequestItems;
    }

    public void setTicketRequestItems(ArrayList<RequestItem> ticketRequestItems) {
        this.ticketRequestItems = ticketRequestItems;
    }

    public ArrayList<RequestItem> getFoodRequestItems() {
        return foodRequestItems;
    }

    public void setFoodRequestItems(ArrayList<RequestItem> foodRequestItems) {
        this.foodRequestItems = foodRequestItems;
    }

    public ArrayList<Seat> getSeatingRequests() {
        return seatingRequests;
    }

    public void setSeatingRequests(ArrayList<Seat> seatingRequests) {
        this.seatingRequests = seatingRequests;
    }
}
