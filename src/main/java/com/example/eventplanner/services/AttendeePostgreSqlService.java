package com.example.eventplanner.services;

import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
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
    public boolean validateAttendee(String personalCode){
        return this.validateAttendee(new PersonalCode(personalCode));
    }

    @Override
    public boolean validateAttendee(PersonalCode personalCode){
        return attendeeRepository.findByCode(personalCode).isPresent();
    }

}
