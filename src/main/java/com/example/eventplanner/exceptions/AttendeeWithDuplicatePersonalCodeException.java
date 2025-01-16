package com.example.eventplanner.exceptions;

import com.example.eventplanner.domain.PersonalCode;
import jakarta.validation.ConstraintViolationException;

public class AttendeeWithDuplicatePersonalCodeException extends RuntimeException {
    public AttendeeWithDuplicatePersonalCodeException(PersonalCode personalCode) {
        super("Attendee with personal code already exists: " + personalCode.toString(), null);
    }

}
