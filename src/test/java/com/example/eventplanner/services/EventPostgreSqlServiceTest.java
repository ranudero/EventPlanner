package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        today = LocalDateTime.now();
        todayString = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        todayPlusTwoDays = today.plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        todayPlusTwoDaysString = todayPlusTwoDays.format(formatter);

        newEvent = new SignupNewEventCommand(
                "Test Event",
                todayPlusTwoDaysString,
                List.of("PVJ9","PVJ8","PVJ7","PVJ9"));
    }

    @Test
    @DisplayName("Test if createEvent method returns the correct DTO")
    void testCreateEvent_happyFlow() {
        //Given
        when(attendeeService.validateAttendee(any(String.class))).thenReturn(true);
        when(eventRepository.save(any())).thenReturn(new Event(
                1L,
                "Test Event",
                todayPlusTwoDays,
                Set.of(
                        new Attendee("Lander", new PersonalCode("PVJ9")),
                        new Attendee("Nick", new PersonalCode("PVJ8")),
                        new Attendee("Sissi", new PersonalCode("PVJ7"))
                )));
        // When
        CreatedEventDTO actualDTO = eventPostgreSqlService.createEvent(newEvent);
        // Then
        CreatedEventDTO expectedDTO = new CreatedEventDTO(
                1,
                "Test Event",
                todayPlusTwoDays,
                3);

        assertEquals(expectedDTO.name(), actualDTO.name());
        assertEquals(expectedDTO.date(), actualDTO.date());
        assertEquals(expectedDTO.numberOfInvitees(), actualDTO.numberOfInvitees());

    }

    @Test
    @DisplayName("Test if createEvent method throws exception when creating event for today")
    void testCreateEventForToday_unHappyFlow(){
        //given
        when(attendeeService.validateAttendee(any(String.class))).thenReturn(true);
        when(eventRepository.save(any())).thenReturn(new Event(
                1L,
                "Test Event",
                todayPlusTwoDays,
                Set.of(
                        new Attendee("Lander", new PersonalCode("PVJ9")),
                        new Attendee("Nick", new PersonalCode("PVJ8")),
                        new Attendee("Sissi", new PersonalCode("PVJ7"))
                )));
        newEvent = new SignupNewEventCommand(
                "Test Event",
                todayString,
                List.of("PVJ9","PVJ8","PVJ7"));

        assertThrows(IllegalArgumentException.class, () -> {
            eventPostgreSqlService.createEvent(newEvent);
        }, "Invalid date");
    }

    @Test
    @DisplayName("Test if createEvent method throws exception when attendees are not known")
    void testCreateEventWithInvalidAttendees_unHappyFlow(){
        //given
        newEvent = new SignupNewEventCommand(
                "Test Event",
                todayPlusTwoDaysString,
                List.of("PVJ9","PVJ8","PVJ7"));

        assertThrows(IllegalArgumentException.class, () -> {
            eventPostgreSqlService.createEvent(newEvent);
        }, "Invalid attendees");
    }


}
