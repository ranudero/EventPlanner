package com.example.eventplanner.services;

import com.example.eventplanner.domain.PersonalCode;

public interface AttendeeService {
    void addAttendee(String name, PersonalCode personalCode);
}
