package com.movinder.be.exception.Cinema;

public class CinemaNotFoundException extends RuntimeException{
    public CinemaNotFoundException() {
        super("Cinema not found with the provided ID");
    }

}
