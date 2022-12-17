package com.movinder.be.exception.Cinema;

public class CinemaDataNotCompleteException extends RuntimeException{
    public CinemaDataNotCompleteException() {
        super("Cinema data not complete");
    }

}
