package com.movinder.be.exception;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(String domain) {
        super("Cannot find resource base on ID for " + domain);
    }

}
