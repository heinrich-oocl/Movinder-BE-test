package com.movinder.be.exception;

public class InvalidIDException extends RuntimeException{
    public InvalidIDException(String idProvided) {
        super("Invalid ID Provided, id provided:"+ idProvided);
    }

}
