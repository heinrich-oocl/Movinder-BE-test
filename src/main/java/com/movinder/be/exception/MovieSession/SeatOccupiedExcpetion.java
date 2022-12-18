package com.movinder.be.exception.MovieSession;

import com.movinder.be.entity.Seat;

public class SeatOccupiedExcpetion extends RuntimeException{
    public SeatOccupiedExcpetion(Seat seat) {
        super("Seat is no longer available col: " + seat.getColumn() + " row: " + seat.getRow());
    }

}
