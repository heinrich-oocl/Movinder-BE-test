package com.movinder.be.exception.MovieSession;

public class MovieSessionDataNotCompleteException extends RuntimeException{
    public MovieSessionDataNotCompleteException() {
        super("Movie session data not complete");
    }

}
