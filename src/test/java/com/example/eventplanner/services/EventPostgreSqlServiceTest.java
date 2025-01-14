package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.EventRepository;
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
        //given
        CreatedEventDTO expectedResult = new CreatedEventDTO(1L, "Test Event", todayPlusTwoDays, 3);
        //when
        when()
        CreatedEventDTO result = eventPostgreSqlService.createEvent(newEvent);
        //then
        assertEquals(expectedResult.name(), result.name());
        assertEquals(expectedResult.date(), result.date());
        assertEquals(expectedResult.numberOfInvitees(), result.numberOfInvitees());
    }



}
