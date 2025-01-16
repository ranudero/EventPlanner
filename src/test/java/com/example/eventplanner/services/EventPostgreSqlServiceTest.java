package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.EventRepository;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit-tests")
public class EventPostgreSqlServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private AttendeeService attendeeService;

    @InjectMocks
    private EventPostgreSqlService eventPostgreSqlService;

    private SignupNewEventCommand newEvent;
    private LocalDateTime todayPlusTwoDays;
    private LocalDateTime today;
    private String todayString;
    private String todayPlusTwoDaysString;
    Set<Attendee> mockAttendees;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockAttendees = Set.of(
                new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9")),
                new Attendee("Nick Verbrugghe", new PersonalCode("PVJ8")),
                new Attendee("Sissi Verbrugghe", new PersonalCode("PVJ7"))
        );

        today = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        todayString = CustomDateTimeFormatter.formatToDate(today);
        todayPlusTwoDays = today.plusDays(2).truncatedTo(ChronoUnit.SECONDS);
        todayPlusTwoDaysString = CustomDateTimeFormatter.formatToDate(todayPlusTwoDays);

        Set<String> attendeeCodes = new TreeSet<>(List.of("PVJ9","PVJ8","PVJ7","PVJ9","PVJ8","PVJ7"));

        newEvent = new SignupNewEventCommand(
                "Test Event",
                todayPlusTwoDaysString,
                attendeeCodes);
    }

    /*
    todo: Add tests for testing the UseCases
      - [x] Creating a correct event
      - [X] Check if the list of attendees removes duplications
      - [ ] Check if the list of attendees containing a non existent attendee, throws an exception
      - [x] Check if a start date on today, throws an exception
      - [x] Check if a start date in the past, throws an exception
     */

    @Test
    @DisplayName("Test if createEvent method returns the correct DTO")
    void testCreateEvent_happyFlow() {
        //given
        CreatedEventDTO expectedResult = new CreatedEventDTO(1L, "Test Event", todayPlusTwoDays, 3);

        //when
        doAnswer(invocation -> {
            PersonalCode attendeeCode = invocation.getArgument(0);
            return mockAttendees.stream()
                    .filter(
                            a -> a.getCode().equals(attendeeCode)
                    ).findFirst()
                    .get();
        }).when(attendeeService).getAttendeeIfExists(any());
        doAnswer(invocation -> {
            Event event = invocation.getArgument(0);
            event.setId(1L);
            return event;
        }).when(eventRepository).save(any(Event.class));
        CreatedEventDTO result = eventPostgreSqlService.createEvent(newEvent);

        //then
        assertNotNull(result);
        assertEquals(expectedResult.id(), result.id());
        assertEquals(expectedResult.name(), result.name());
        assertEquals(expectedResult.date(), result.date());
        assertEquals(expectedResult.numberOfInvitees(), result.numberOfInvitees());
        verify(attendeeService, times(3)).getAttendeeIfExists(any());
    }



}
