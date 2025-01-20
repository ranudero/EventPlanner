package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.RetrievedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.exceptions.AttendeeWithPersonalCodeNotFoundException;
import com.example.eventplanner.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new AttendeeWithPersonalCodeNotFoundException(personalCode));
    }

    @Override
    public RetrievedAttendeeDTO fetchAttendee(String personalCode) {
        PersonalCode code = new PersonalCode(personalCode);
        Attendee attendee = getAttendeeIfExists(code);
        return RetrievedAttendeeDTO.from(attendee);
    }

}
