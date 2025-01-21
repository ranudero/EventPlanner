package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.exceptions.AttendeeWithDuplicatePersonalCodeException;
import com.example.eventplanner.exceptions.AttendeeWithPersonalCodeNotFoundException;
import com.example.eventplanner.repositories.AttendeeRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit-tests")
public class AttendeePostgreSqlServiceTest {
    @Mock
    private AttendeeRepository attendeeRepository;

    @InjectMocks
    private AttendeePostgreSqlService attendeePostgreSqlService;

    Set<Attendee> mockAttendees;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockAttendees = Set.of(
                new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9")),
                new Attendee("Nick Verbrugghe", new PersonalCode("PVJ8")),
                new Attendee("Sissi Verbrugghe", new PersonalCode("PVJ7"))
        );
    }

    @Test
    @DisplayName("Test if addAttendee method saves the attendee")
    void testAddAttendee_happyFlow(){
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

    @Test
    @DisplayName("Test if getAttendeeIfExists method throws exception when attendee does not exist")
    void testGetAttendeeIfExists_unHappyFlow() {
        // Given
        PersonalCode code = new PersonalCode("PVJ3");
        Attendee expectedAttendee = new Attendee("Lander Verbrugghe", code);

        //when
        assertThrows(AttendeeWithPersonalCodeNotFoundException.class, () -> {
            attendeePostgreSqlService.getAttendeeIfExists(code);
        });
    }

    @Test
    @DisplayName("Test if addAttendee throws exception when Personal Code already exists")
    void testAddAttendeeWithExistingPersonalCode_ShouldThrowException() {
        SignupNewAttendeeCommand newAttendee = new SignupNewAttendeeCommand(
                "Lander Verbrugghe",
                "PVJ9"
        );

        // Mock that when the save method is called, it throws a DataIntegrityViolationException because the personal code already exists
        when(attendeeRepository.save(any())).thenThrow(new DataIntegrityViolationException(any()));

        assertThrows(AttendeeWithDuplicatePersonalCodeException.class, () -> attendeePostgreSqlService.addAttendee(newAttendee));

    }
}
