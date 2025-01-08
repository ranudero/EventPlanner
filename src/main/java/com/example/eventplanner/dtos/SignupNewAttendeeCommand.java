package com.example.eventplanner.dtos;
import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import jakarta.validation.constraints.NotBlank;

public record SignupNewAttendeeCommand(
        @NotBlank String name,
        @NotBlank String personalCode
) {
    public Attendee toAttendee() {
        return new Attendee(name, new PersonalCode(personalCode));
    }
}
