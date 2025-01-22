package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.RetrievedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.exceptions.AttendeeWithDuplicatePersonalCodeException;
import com.example.eventplanner.exceptions.AttendeeWithPersonalCodeNotFoundException;
import com.example.eventplanner.repositories.AttendeeRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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

    @Nested
    class FetchAttendeeUseCase {

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
        @DisplayName("Test if fetchAttendee returns the correct outputformat.")
        void testFetchAttendee_happyFlow(){
            // Given
            String code = "PVJ9";
            Attendee repositoryAttendeeResult = new Attendee("Lander Verbrugghe", new PersonalCode(code));
            List<Event> expectedEvents = List.of(new Event("Birthday Party", LocalDateTime.now(), Set.of(repositoryAttendeeResult)));
            repositoryAttendeeResult.subscribeToEvent(expectedEvents.get(0));
            RetrievedAttendeeDTO expectation = new RetrievedAttendeeDTO(
                    code,
                    "Lander Verbrugghe",
                    expectedEvents
            );

            // when
            when(attendeeRepository.findByCode(any())).thenReturn(Optional.of(repositoryAttendeeResult));
            RetrievedAttendeeDTO actualAttendee = attendeePostgreSqlService.fetchAttendee(code);

            // Then
            assertNotNull(actualAttendee);
            assertEquals(expectation.attendeeName(), actualAttendee.attendeeName());
            assertEquals(expectation.personalCode(), actualAttendee.personalCode());
            assertEquals(expectedEvents.size(), actualAttendee.invitedEvents().size());
            assertIterableEquals(expectedEvents, actualAttendee.invitedEvents());

        }

        @Test
        @DisplayName("Test if service throws exception when attendee does not exist")
        void testFetchAttendeeUnknownPersonalCode_unHappyFlow(){

            when(attendeeRepository.findByCode(any())).thenReturn(Optional.empty());
            assertThrows(AttendeeWithPersonalCodeNotFoundException.class,() -> attendeePostgreSqlService.fetchAttendee("PVJ3"));
        }

    }

    @Nested
    class AddAttendeeUseCase{
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
        @DisplayName("Given Attendee with incorrect personal code then expect IllegalArgumentException")
        void testAddAttendeeWithWrongPersonalCode_unHappyFlow(){
            //Given
            String name = "Lander Verbrugghe";
            String code = "PVJ999";

            //When Then
            assertThrows(IllegalArgumentException.class,() -> attendeePostgreSqlService.addAttendee(new SignupNewAttendeeCommand(name,code)));
        }
    }

}
