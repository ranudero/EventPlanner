package com.example.eventplanner.apis;

import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventPostgreSqlService;

    @PostMapping
    public CreatedEventDTO createEvent(@RequestBody SignupNewEventCommand newEvent) {
        return eventPostgreSqlService.createEvent(newEvent);
    }
}
