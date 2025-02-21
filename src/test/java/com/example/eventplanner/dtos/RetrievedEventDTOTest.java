package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit-tests")
class RetrievedEventDTOTest {

    @Test
    @DisplayName("Test if the Event is returned in the RetrievedEventDTO format.")
    void testRetrievedEventDTOFrom() {
        //Given
        Attendee attendee = new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9"));
        LocalDateTime today = LocalDateTime.now();
        String todayString = CustomDateTimeFormatter.formatToDate(today);
        String name = "Test event";
        int numberOfAttendees = 1;
        Event event = new Event(
                name,
                today,
                Set.of(attendee)
        );
        RetrievedEventDTO expectedDTO = new RetrievedEventDTO(
                name,
                todayString,
                numberOfAttendees,
                Set.of(AttendeeDTO.from(attendee))
        );

        //when
        RetrievedEventDTO result = RetrievedEventDTO.from(event);

        //Then
        assertEquals(expectedDTO,result);
    }
}