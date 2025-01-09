package com.example.eventplanner.domain;

import com.example.eventplanner.repositories.AttendeeRepository;
import com.example.eventplanner.services.AttendeePostgreSqlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

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
    @DisplayName("Test if Attendee is not created with valid name and invalid personal code")
    void testAttendeeCreation_invalidPersonalCode() {
        // Given
        String name = "Lander Verbrugghe";

        // Then
        assertThrows(IllegalArgumentException.class, () -> new PersonalCode("PVJ95"));
    }

    @Test
    @DisplayName("Test if Attendee not is created with valid name and empty personal code")
    void testAttendeeCreation_emptyPersonalCode() {
        // Given
        String name = "Lander Verbrugghe";

        // Then
        assertThrows(IllegalArgumentException.class, () -> new PersonalCode(""));
    }

}
