package com.movinder.be.exception;

public class MalformedRequestException extends RuntimeException{
    public MalformedRequestException(String error) {
        super(error);
    }

}
