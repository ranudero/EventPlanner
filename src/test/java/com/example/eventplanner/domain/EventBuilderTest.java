package com.example.eventplanner.domain;

import com.example.eventplanner.exceptions.DateNotInFutureException;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit-tests")
class EventBuilderTest {

    private final LocalDateTime today = LocalDateTime.now();
    private final String todayString = CustomDateTimeFormatter.formatToDate(today);
    private final LocalDateTime dateInPast = LocalDateTime.now().minusDays(7);
    private final String dateInPastString = CustomDateTimeFormatter.formatToDate(dateInPast);
    private final LocalDateTime dateInFuture = LocalDateTime.now().plusDays(7);
    private final String dateInFutureString = CustomDateTimeFormatter.formatToDate(dateInFuture);

    private String name;
    private Set<Attendee> attendees;
    private String start;

    @BeforeEach
    void setUp() {
        name = "Test Event";
        attendees = Set.of(new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9")),
                new Attendee("Nick Bauters", new PersonalCode("PVJ8")));
    }

    @Test
    @DisplayName("Test if Event is created with valid name, start date and attendees")
    void testEventBuilder_happyFlow() {
        //given
        start = dateInFutureString;
        //when
        Event event = new EventBuilder()
                .withName(name)
                .withStart(start)
                .withAttendeeList(attendees)
                .build();
        //then
        assertEquals(name, event.getName());
        assertEquals(start, CustomDateTimeFormatter.formatToDate(event.getStart()));
        assertEquals(attendees.size(), event.getAttendeeList().size());
    }

    @Test
    @DisplayName("Test if error message is triggered when creating an event in the past")
    void testEventBuilderDateInPast_unHappyFlow() {
        //given
        start = dateInPastString;
        //when & then
        checkDateNotInFutureException();
    }

    @Test
    @DisplayName("Test if error message is triggered when creating an event on the current date")
    void testEventBuilderDateIsCurrent_unHappyFlow() {
        //given
        start = todayString;
        //when & then
       checkDateNotInFutureException();
    }

    @Test
    void testWithName() {
        //given
        String name = "Test Event";
        EventBuilder eventBuilder = new EventBuilder();
        //when
        EventBuilder result = eventBuilder.withName(name);
        //then
        assertEquals(name, result.getName());
    }

    @Test
    void testwithStart() {
        //given
        String start = "2025-12-12 12:12:12";
        EventBuilder eventBuilder = new EventBuilder();
        //when
        EventBuilder result = eventBuilder.withStart(start);
        //then
        String resultString = CustomDateTimeFormatter.formatToDate(result.getStart());
        assertEquals(start, resultString);
    }


    @Test
    void withAttendeeList() {
        //given
        Set<Attendee> attendee = Set.of(new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9")));
        EventBuilder eventBuilder = new EventBuilder();
        //when
        EventBuilder result = eventBuilder.withAttendeeList(attendee);
        //then
        assertEquals(attendee, result.getAttendeeList());
    }

    void checkDateNotInFutureException(){
        assertThrows(DateNotInFutureException.class, () -> {

            new EventBuilder()
                    .withName(name)
                    .withStart(start)
                    .withAttendeeList(attendees)
                    .build();
        });
    }
}