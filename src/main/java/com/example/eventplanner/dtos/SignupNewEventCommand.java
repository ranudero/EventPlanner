package com.example.eventplanner.dtos;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.List;

public record SignupNewEventCommand(
        @NotBlank String name,
        @NotBlank String date,
        @NotBlank Set<String> attendees
) {

}
