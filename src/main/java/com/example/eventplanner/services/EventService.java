package com.example.eventplanner.services;

import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.RetrievedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;

public interface EventService {
    CreatedEventDTO createEvent(SignupNewEventCommand newEvent);
    RetrievedEventDTO fetchEvent(String eventName);

}
