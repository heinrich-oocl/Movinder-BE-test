package com.movinder.be.exception;

public class CustomerNameAlreadyExistException extends RuntimeException{
    public CustomerNameAlreadyExistException() {
        super("Customer name already exist");
    }

}
