package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Attendee;

public record AttendeeDTO(String name, String code) {
    public static AttendeeDTO from(Attendee attendee) {
        return new AttendeeDTO((attendee.getName()),attendee.getPersonalCode().toString());
    }
}
