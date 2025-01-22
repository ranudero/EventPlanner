package com.example.eventplanner.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


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
        assertEquals(name, attendee.getName(), "Name is not set correctly");
        assertEquals(code, attendee.getCode(), "Personal code is not set correctly");
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

    @Nested
    @DisplayName("Test if Attendee compares correctly to other Attendees")
    class AttendeeComparableTests {
        Attendee attendee = new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9"));
        Attendee exactCopyAttendee = new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9"));
        Attendee differentNameAttendee = new Attendee("Nick Bauters", new PersonalCode("PVJ9"));
        Attendee differentCodeAttendee = new Attendee("Lander Verbrugghe", new PersonalCode("PVJ5"));
        Attendee completeDifferentAttendee = new Attendee("Nick Bauters", new PersonalCode("PVJ5"));

        @Test
        @DisplayName("Test if CompareTo works correctly")
        void testCompareTo() {
            assertTrue(attendee.compareTo(exactCopyAttendee) == 0, "Attendee should be greater than other attendee");
            assertTrue(attendee.compareTo(differentNameAttendee) == 0, "Attendee should be greater than other attendee");
            assertTrue(attendee.compareTo(differentCodeAttendee) != 0, "Attendee should be greater than other attendee");
            assertTrue(attendee.compareTo(completeDifferentAttendee) != 0, "Attendee should be less than other attendee");
        }

        @Test
        @DisplayName("Test if equals works correctly")
        void testEquals() {
            assertTrue(attendee.equals(exactCopyAttendee), "Attendee should be equal");
            assertTrue(attendee.equals(differentNameAttendee), "Attendee should be equal for the comparison");
            assertFalse(attendee.equals(differentCodeAttendee), "Attendee should be different from the other attendee");
            assertFalse(attendee.equals(completeDifferentAttendee), "Attendee should be different from the other attendee");
        }

    }

    @Test
    @DisplayName("Subscribe an attendee to an event")
    void testSubscribeToEvent_happyFlow() {
        // Given
        Attendee attendee = new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9"));
        Event event = new Event("Birthday Party", LocalDateTime.now(), Set.of(attendee));

        // When
        attendee.subscribeToEvent(event);

        //Then
        assertNotNull(attendee.getEvents());
        assertNotEquals(0, attendee.getEvents().size());
        assertTrue(attendee.getEvents().contains(event), "Attendee should be subscribed to the event");

    }
}
