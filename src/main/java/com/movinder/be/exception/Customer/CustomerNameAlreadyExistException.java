package com.movinder.be.exception.Customer;

public class CustomerNameAlreadyExistException extends RuntimeException{
    public CustomerNameAlreadyExistException() {
        super("Customer name already exist");
    }

}
