package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Attendee;

public record CreatedAttendeeDTO(String name, String code) {

    public static CreatedAttendeeDTO from(Attendee attendee) {
        return new CreatedAttendeeDTO(attendee.getName(), attendee.getCode());
    }
}
