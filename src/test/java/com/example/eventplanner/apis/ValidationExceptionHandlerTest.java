package com.example.eventplanner.apis;

import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.services.EventService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("unit-tests")
@WebMvcTest(EventController.class)
public class ValidationExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Test
    public void whenInvalidDate_thenReturnsBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Start date should be at least tomorrow or further in the future."))
                .when(eventService).createEvent(any(SignupNewEventCommand.class));

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Event\", \"date\": \"2023-01-01 12:00:00\", \"attendees\": [\"PVJ9\"]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Start date should be at least tomorrow or further in the future."));
    }

    @Test
    public void whenInvalidAttendees_thenReturnsBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Invalid attendees"))
                .when(eventService).createEvent(any(SignupNewEventCommand.class));

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Event\", \"date\": \"2025-01-01 12:00:00\", \"attendees\": [\"INVALID\"]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid attendees"));
    }
}