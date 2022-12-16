package com.movinder.be.exception;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException() {
        super("Incorrect credentials provided");
    }

}
