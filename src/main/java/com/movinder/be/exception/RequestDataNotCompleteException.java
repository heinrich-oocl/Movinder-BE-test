package com.movinder.be.exception;

public class RequestDataNotCompleteException extends RuntimeException{
    public RequestDataNotCompleteException(String domain) {
        super(domain + " request not complete, some field(s) are null");
    }

}
