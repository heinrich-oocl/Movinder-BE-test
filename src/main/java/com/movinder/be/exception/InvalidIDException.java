package com.movinder.be.exception;

public class InvalidIDException extends RuntimeException{
    public InvalidIDException() {
        super("Invalid ID Provided");
    }

}
