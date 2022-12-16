package com.movinder.be.exception.Customer;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException() {
        super("Incorrect credentials provided");
    }

}
