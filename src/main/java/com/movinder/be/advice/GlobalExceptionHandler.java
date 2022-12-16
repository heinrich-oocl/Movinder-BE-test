package com.movinder.be.advice;

import com.movinder.be.exception.CustomerDataNotCompleteException;
import com.movinder.be.exception.CustomerNameAlreadyExistException;
import com.movinder.be.exception.CustomerNotFoundException;
import com.movinder.be.exception.WrongCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse idNotFound(Exception exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler({CustomerDataNotCompleteException.class, CustomerNameAlreadyExistException.class, WrongCredentialsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse malformedRequest(Exception exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler({WrongCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse authenticationError(Exception exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

}
