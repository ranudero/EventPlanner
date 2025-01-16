package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.exceptions.AttendeeWithDuplicatePersonalCodeException;


public interface AttendeeService {
    CreatedAttendeeDTO addAttendee(SignupNewAttendeeCommand newAttendee) throws AttendeeWithDuplicatePersonalCodeException;
    Attendee getAttendeeIfExists(PersonalCode personalCode);
}
