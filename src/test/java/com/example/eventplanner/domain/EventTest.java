package com.example.eventplanner.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit-tests")
public class EventTest {
    private Event event;

    @BeforeEach
    void init() {
        event = new Event(
                "Test Event",
                LocalDateTime.now().plusDays(2),
                Set.of(
                        new Attendee("Lander", new PersonalCode("PVJ9")),
                        new Attendee("Nick", new PersonalCode("PVJ8")),
                        new Attendee("Sissi", new PersonalCode("PVJ7"))
                ));
    }

    @Test
    void TestEventHasNumberOfInvitees_HappyFlow() {

        //when
        assertEquals(3, event.getNumberOfInvitees());
    }


}
