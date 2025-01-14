package com.example.eventplanner.services;

import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeePostgreSqlService implements AttendeeService {
    private final AttendeeRepository attendeeRepository;

    @Override
    public CreatedAttendeeDTO addAttendee(SignupNewAttendeeCommand newAttendee) {
        Attendee attendee = newAttendee.toAttendee();
        attendeeRepository.save(attendee);
        return CreatedAttendeeDTO.from(attendee);
    }

    @Override
    public Attendee getAttendeeIfExists(PersonalCode personalCode){
        return attendeeRepository.findByCode(personalCode)
                .orElseThrow(() -> new IllegalArgumentException("Attendee with personalCode not found: " + personalCode));
    }

}
