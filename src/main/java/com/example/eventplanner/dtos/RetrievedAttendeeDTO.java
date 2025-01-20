package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;

import java.util.List;

public record RetrievedAttendeeDTO(String personalCode,
                                   String attendeeName,
                                   List<Event> invitedEvents) {
    public static RetrievedAttendeeDTO from(Attendee attendee) {
        return new RetrievedAttendeeDTO(attendee.getPersonalCode().getCode(), attendee.getName(), attendee.getEvents());
    }
}
