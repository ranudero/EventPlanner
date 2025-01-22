package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;

import java.util.List;

public interface EventService {
    CreatedEventDTO createEvent(SignupNewEventCommand newEvent);

}
