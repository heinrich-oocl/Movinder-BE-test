package com.movinder.be.exception.movie;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException() {
        super("Movie not found for the ID provided");
    }

}
