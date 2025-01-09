package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Event;

public record CreatedEventDTO(
        String id,
        String name,
        String date
        //String numberOfInvitees
) {
    public static CreatedEventDTO from(Event event) {
        return new CreatedEventDTO(event.getId().toString(), event.getName(), event.getDate());
    }
}
