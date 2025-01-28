package com.example.eventplanner.exceptions;

import com.example.eventplanner.domain.PersonalCode;

public class AttendeeWithDuplicatePersonalCodeException extends RuntimeException {
    public AttendeeWithDuplicatePersonalCodeException(PersonalCode personalCode) {
        super("Attendee with personal code already exists: " + personalCode.toString(), null);
    }

}
