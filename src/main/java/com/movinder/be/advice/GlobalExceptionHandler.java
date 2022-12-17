package com.movinder.be.advice;

import com.movinder.be.exception.Cinema.CinemaNotFoundException;
import com.movinder.be.exception.InvalidIDException;
import com.movinder.be.exception.Customer.CustomerNameAlreadyExistException;
import com.movinder.be.exception.Customer.CustomerNotFoundException;
import com.movinder.be.exception.Customer.WrongCredentialsException;
import com.movinder.be.exception.MalformedRequestException;
import com.movinder.be.exception.MovieSession.SessionNotFoundEexception;
import com.movinder.be.exception.RequestDataNotCompleteException;
import com.movinder.be.exception.movie.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class, CinemaNotFoundException.class, SessionNotFoundEexception.class, MovieNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse idNotFound(Exception exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler({InvalidIDException.class,
            MalformedRequestException.class,
            CustomerNameAlreadyExistException.class,
            RequestDataNotCompleteException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse malformedRequest(Exception exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler({WrongCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse authenticationError(Exception exception) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }

}
