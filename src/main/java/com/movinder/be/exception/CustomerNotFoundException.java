package com.movinder.be.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
