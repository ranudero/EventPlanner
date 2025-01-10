package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;

import java.util.List;

public interface AttendeeService {
    CreatedAttendeeDTO addAttendee(SignupNewAttendeeCommand newAttendee);
    boolean validateAttendee(String personalCode);
    boolean validateAttendee(PersonalCode personalCode);
}
