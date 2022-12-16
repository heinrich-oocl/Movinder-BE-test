package com.movinder.be.exception.Customer;

public class CustomerDataNotCompleteException extends RuntimeException{
    public CustomerDataNotCompleteException() {
        super("Customer data not complete");
    }

}
