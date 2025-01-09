package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.AttendeeRepository;
import com.example.eventplanner.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventPostgreSqlService implements EventService {

    private final AttendeeService attendeeService;
    private final AttendeeRepository attendeeRepository;
    private final EventRepository eventRepository;

    @Override
    public CreatedEventDTO addEvent(SignupNewEventCommand newEvent) {
        return null;
    }

    @Override
    public List<Attendee> removeDuplicates(List<Attendee> attendees) {
        return attendees.stream()
                .distinct()
                .toList();
    }

    @Override
    public boolean validateAttendees(SignupNewEventCommand newEvent) {
        return newEvent.attendees().stream()
                .allMatch(code -> attendeeRepository.findByCode(new PersonalCode(code)).isPresent());
    }
}
