package com.example.eventplanner.services;


import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.EventBuilder;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.EventRepository;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import com.example.eventplanner.utils.WrongDateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventPostgreSqlService implements EventService {
    private final AttendeeService attendeeService;
    private final EventRepository eventRepository;

    @Override
    public CreatedEventDTO createEvent(SignupNewEventCommand newEvent) {
        if (!validateDateEvent(newEvent)) {
            throw new WrongDateException("Start date should be at least tomorrow or further in the future.");
        }
        Set<Attendee> attendees = getAttendeesForEvent(newEvent);
        Event event = new EventBuilder()
                .withName(newEvent.name())
                .withStart(newEvent.date())
                .withAttendeeList(attendees)
                .build();
        eventRepository.save(event);

        return CreatedEventDTO.from(event);

    }
    
    private Set<Attendee> getAttendeesForEvent(SignupNewEventCommand newEvent) {
        return newEvent.attendees().stream()
                .map(PersonalCode::new)
                .map(attendeeService::getAttendeeIfExists)
                .collect(Collectors.toSet());
    }

    private boolean validateDateEvent(SignupNewEventCommand newEvent) {
        LocalDate eventDate = CustomDateTimeFormatter.parseToDateTime(newEvent.date()).toLocalDate();
        return eventDate.isAfter(LocalDate.now().plusDays(1));
    }
}
