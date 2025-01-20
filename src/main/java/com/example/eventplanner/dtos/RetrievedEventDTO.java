package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;

import java.time.LocalDateTime;
import java.util.Set;

public record RetrievedEventDTO(String name,
                                LocalDateTime startDate,
                                int numberOfInvitees,
                                Set<Attendee> attendeeList
                                ) {
    public static RetrievedEventDTO from(Event event) {
        return new RetrievedEventDTO(event.getName(),event.getStart(),event.getNumberOfInvitees(),event.getAttendeeList());
    }
}
