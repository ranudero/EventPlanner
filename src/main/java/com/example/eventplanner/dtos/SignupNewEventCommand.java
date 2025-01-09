package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import jakarta.validation.constraints.NotBlank;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.List;

public record SignupNewEventCommand(
        @NotBlank String name,
        @NotBlank String date,
        @NotBlank List<String> attendees
) {
    public Event toEvent() {
        LocalDateTime start = LocalDateTime.parse(this.date);
        List<Attendee> attendeeList = this.attendees.stream()
                .map(code -> new Attendee("attendee", new PersonalCode(code)))
                .collect(Collectors.toList());
        return new Event(name, start, attendeeList);
    }
}
