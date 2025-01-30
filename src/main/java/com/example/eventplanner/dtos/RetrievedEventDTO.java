package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Event;

import java.util.Set;
import java.util.stream.Collectors;

public record RetrievedEventDTO(String name,
                                String startDate,
                                int numberOfInvitees,
                                Set<AttendeeDTO> attendeeList
                                ) {
    public static RetrievedEventDTO from(Event event) {
        Set<AttendeeDTO> attendeeDTOs = event.getAttendeeList().stream()
                .map(AttendeeDTO::from)
                .collect(Collectors.toSet());
        return new RetrievedEventDTO(event.getName(),event.getStart(),event.getNumberOfInvitees(),attendeeDTOs);
    }
}
