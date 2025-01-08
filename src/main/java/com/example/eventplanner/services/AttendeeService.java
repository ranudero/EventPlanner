package com.example.eventplanner.services;

import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;

public interface AttendeeService {
    CreatedAttendeeDTO addAttendee(SignupNewAttendeeCommand newAttendee);
}
