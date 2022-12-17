package com.movinder.be.exception.MovieSession;

public class SessionNotFoundError extends RuntimeException{
    public SessionNotFoundError() {
        super("Session not found for the ID provided");
    }

}
