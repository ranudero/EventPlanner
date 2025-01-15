package com.example.eventplanner.services;


import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.EventBuilder;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventPostgreSqlService implements EventService {
    private final AttendeeService attendeeService;
    private final EventRepository eventRepository;

    @Override
    public CreatedEventDTO createEvent(SignupNewEventCommand newEvent) {

        Set<Attendee> attendees = getAttendeesForEvent(newEvent);
        Event event = new EventBuilder()
                .withName(newEvent.name())
                .withStart(newEvent.date())
                .withAttendeeList(attendees)
                .build();
        event = eventRepository.save(event);
        return CreatedEventDTO.from(event);

    }
    
    private Set<Attendee> getAttendeesForEvent(SignupNewEventCommand newEvent) {
        return newEvent.attendees().stream()
                .map(PersonalCode::new)
                .map(attendeeService::getAttendeeIfExists)
                .collect(Collectors.toSet());
    }

}
