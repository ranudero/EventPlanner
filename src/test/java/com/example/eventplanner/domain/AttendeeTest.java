package com.example.eventplanner.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit-tests")
public class AttendeeTest {

    @Test
    @DisplayName("Test if Attendee is created with valid name and personal code")
    void testAttendeeCreation_happyFlow() {
        // Given
        String name = "Lander Verbrugghe";
        PersonalCode code = new PersonalCode("PVJ9");

        // When
        Attendee attendee = new Attendee(name, code);

        // Then
        assert attendee.getName().equals(name);
        assert attendee.getCode().equals(code.getCode());
    }

    @Test
    @DisplayName("Test if Attendee is created with valid name and invalid personal code")
    void testAttendeeCreation_invalidPersonalCode() {
        // Given
        String name = "Lander Verbrugghe";
        PersonalCode code = new PersonalCode("PVJ95");

        // When
        Attendee attendee = new Attendee(name, code);

        // Then
        assert attendee.getName().equals(name);
        assert !attendee.getCode().equals("PVJ8");
    }
}
