package com.movinder.be.exception.MovieSession;

public class SessionNotFoundEexception extends RuntimeException{
    public SessionNotFoundEexception() {
        super("Session not found for the ID provided");
    }

}
