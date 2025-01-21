package com.example.eventplanner.services;


import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.EventBuilder;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.RetrievedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.exceptions.EventWithNameNotFoundException;
import com.example.eventplanner.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public Event getEventByName(String eventName) {
        List<Event> events = eventRepository.findAllByName(eventName);
        if (events.isEmpty()) {
            throw new EventWithNameNotFoundException(eventName);
        }
        return events.stream()
                .min(Comparator.comparing(event -> Math.abs(ChronoUnit.SECONDS.between(event.getStart(), LocalDateTime.now()))))
                .orElseThrow(() -> new EventWithNameNotFoundException(eventName));
    }

    public RetrievedEventDTO fetchEvent(String eventName) {
        Event fetchedEvent = getEventByName(eventName);
        return RetrievedEventDTO.from(fetchedEvent);
    }
}
