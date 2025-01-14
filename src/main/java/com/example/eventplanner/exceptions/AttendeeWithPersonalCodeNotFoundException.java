package com.example.eventplanner.exceptions;

import com.example.eventplanner.domain.PersonalCode;

public class AttendeeWithPersonalCodeNotFoundException extends IllegalArgumentException {
    public AttendeeWithPersonalCodeNotFoundException(PersonalCode personalCode) {
        super("Attendee with personalCode not found: " + personalCode);
    }
}
