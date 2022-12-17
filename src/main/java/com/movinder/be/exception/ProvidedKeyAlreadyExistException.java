package com.movinder.be.exception;

public class ProvidedKeyAlreadyExistException extends RuntimeException{
    public ProvidedKeyAlreadyExistException(String domain) {
        super(domain + " already exist");
    }

}
