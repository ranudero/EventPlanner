package com.example.eventplanner.services;


import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.EventRepository;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class EventPostgreSqlService implements EventService {
    private final AttendeeService attendeeService;
    private final EventRepository eventRepository;

    @Override
    public CreatedEventDTO createEvent(SignupNewEventCommand newEvent) {
        if (!validateDateEvent(newEvent)) {
            throw new IllegalArgumentException("Start date should be at least tomorrow or further in the future.");
        }
        if (!validateAttendees(newEvent)) {
            throw new IllegalArgumentException("Invalid attendees");
        }
        return CreatedEventDTO.from(eventRepository.save(newEvent.toEvent()));

    }
    
    private boolean validateAttendees(SignupNewEventCommand newEvent) {
        return newEvent.attendees().stream()
                .allMatch(attendeeService::validateAttendee);
    }

    private boolean validateDateEvent(SignupNewEventCommand newEvent) {
        LocalDate eventDate = CustomDateTimeFormatter.parseToDateTime(newEvent.date()).toLocalDate();
        return eventDate.isAfter(LocalDate.now().plusDays(1));
    }
}
