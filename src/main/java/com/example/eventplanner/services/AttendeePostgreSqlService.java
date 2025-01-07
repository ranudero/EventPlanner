package com.example.eventplanner.services;

import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendeePostgreSqlService implements AttendeeService {
    private final AttendeeRepository attendeeRepository;

    @Override
    public void addAttendee(String name, PersonalCode personalCode) {
        Attendee attendee = new Attendee(name, personalCode);
        attendeeRepository.save(attendee);
    }

}
