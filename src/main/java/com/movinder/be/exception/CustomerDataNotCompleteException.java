package com.movinder.be.exception;

public class CustomerDataNotCompleteException extends RuntimeException{
    public CustomerDataNotCompleteException() {
        super("Customer data not complete");
    }

}
