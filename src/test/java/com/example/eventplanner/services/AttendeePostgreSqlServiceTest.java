package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.repositories.AttendeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit-tests")
public class AttendeePostgreSqlServiceTest {
    @Mock
    private AttendeeRepository attendeeRepository;

    @InjectMocks
    private AttendeePostgreSqlService attendeePostgreSqlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test if addAttendee method saves the attendee")
    void testAddAttendee_happyFlow() {
        // Given
        String name = "Lander Verbrugghe";
        PersonalCode code = new PersonalCode("PVJ9");
        SignupNewAttendeeCommand newAttendee = new SignupNewAttendeeCommand(name, code.getCode());

        // When
        attendeePostgreSqlService.addAttendee(newAttendee);

        // Then
        verify(attendeeRepository).save(any(Attendee.class));
    }

    @Test
    @DisplayName("Test if getAttendeeIfExists method returns the attendee")
    void testGetAttendeeIfExists_happyFlow() {
        // Given
        PersonalCode code = new PersonalCode("PVJ9");
        Attendee expectedAttendee = new Attendee("Lander Verbrugghe", code);
        when(attendeeRepository.findByCode(code)).thenReturn(Optional.of(expectedAttendee));

        // When
        Attendee acutalAttendee = attendeePostgreSqlService.getAttendeeIfExists(code);

        // Then
        assertEquals(expectedAttendee, acutalAttendee);
    }
}
