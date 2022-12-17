package com.movinder.be.exception.MovieSession;

public class SeatOccupiedExcpetion extends RuntimeException{
    public SeatOccupiedExcpetion() {
        super("Seat is no longer available");
    }

}
