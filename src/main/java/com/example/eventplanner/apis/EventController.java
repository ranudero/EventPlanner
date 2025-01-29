package com.example.eventplanner.apis;

import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.RetrievedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    @PostMapping
    public CreatedEventDTO createEvent(@RequestBody SignupNewEventCommand newEvent) {
        return eventService.createEvent(newEvent);
    }

    @GetMapping("/{eventName}")
    public RetrievedEventDTO getEventByName(@PathVariable String eventName){
        return eventService.fetchEvent(eventName);
    }
}
