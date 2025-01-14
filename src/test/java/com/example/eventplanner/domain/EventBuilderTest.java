package com.example.eventplanner.domain;

import com.example.eventplanner.utils.CustomDateTimeFormatter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Tag("unit-tests")
class EventBuilderTest {

    @Test
    void build() {
        //given
        String name = "Test Event";
        String start = "2025-12-12 12:12:12";
        Set<Attendee> attendees = Set.of(new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9")),
                                        new Attendee("Nick Bauters", new PersonalCode("PVJ8")));

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
}