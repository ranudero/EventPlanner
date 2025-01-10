package com.example.eventplanner.services;

import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

@Tag("unit-tests")
public class EventPostgreSqlServiceTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventPostgreSqlService eventPostgreSqlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    SignupNewEventCommand newEvent = new SignupNewEventCommand(
            "Test Event",
            "2025-01-13T12:00:00",
            List.of("PVJ9","PVJ8","PVJ7","PVJ9"));

    @Test
    void testCreateEvent_happyFlow() {
        // When
        eventPostgreSqlService.createEvent(newEvent);
        // Then

    }


}
