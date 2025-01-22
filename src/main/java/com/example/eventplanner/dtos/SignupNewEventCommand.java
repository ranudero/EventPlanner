package com.example.eventplanner.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record SignupNewEventCommand(
        @NotBlank String name,
        @NotBlank String date,
        @NotBlank Set<String> attendees
) {

}
