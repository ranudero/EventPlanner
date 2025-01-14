package com.example.eventplanner.apis;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionDTO handleIllegalArgumentException(IllegalArgumentException ex){
        return new ExceptionDTO(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public record ExceptionDTO(String message, HttpStatus status) {
    }
}
