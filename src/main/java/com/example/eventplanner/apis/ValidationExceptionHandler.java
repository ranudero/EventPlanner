package com.example.eventplanner.apis;

import com.example.eventplanner.exceptions.AttendeeWithDuplicatePersonalCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDTO> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(new ExceptionDTO(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AttendeeWithDuplicatePersonalCodeException.class)
    public ResponseEntity<ExceptionDTO> handleDuplicatePersonalCode(AttendeeWithDuplicatePersonalCodeException ex){
        return new ResponseEntity<>(new ExceptionDTO(ex.getMessage(), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    public record ExceptionDTO(String message, HttpStatus status) {
    }
}
