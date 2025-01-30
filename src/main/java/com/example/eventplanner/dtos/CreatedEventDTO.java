package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Event;

public record CreatedEventDTO(
        long id,
        String name,
        String date,
        int numberOfInvitees
) {
    public static CreatedEventDTO from(Event event) {
        return new CreatedEventDTO(event.getId(), event.getName(), event.getStart(), event.getNumberOfInvitees());
    }
}
