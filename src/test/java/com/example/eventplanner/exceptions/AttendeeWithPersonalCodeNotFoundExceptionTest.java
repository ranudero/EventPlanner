package com.example.eventplanner.exceptions;

import com.example.eventplanner.domain.PersonalCode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit-tests")
class AttendeeWithPersonalCodeNotFoundExceptionTest {

    @Test
    void testAttendeeWithPersonalCodeNotFoundException() {
        AttendeeWithPersonalCodeNotFoundException attendeeWithPersonalCodeNotFoundException = new AttendeeWithPersonalCodeNotFoundException(new PersonalCode("PVJ9"));
        assertEquals("Attendee with personalCode not found: PVJ9", attendeeWithPersonalCodeNotFoundException.getMessage());
    }

}