package com.example.eventplanner.apis;

import com.example.eventplanner.dtos.CreatedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.services.AttendeePostgreSqlService;
import com.example.eventplanner.services.EventPostgreSqlService;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@Tag("integration-tests")
public class EventControllerTest {
    @MockBean
    EventPostgreSqlService eventService;
    @MockBean
    AttendeePostgreSqlService attendeeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createEventTest_happyFlow() throws Exception{
        //Given
        String name = "Test event";
        String date = "2024-03-16 10:30:30";
        LocalDateTime parsedDate = CustomDateTimeFormatter.parseToDateTime(date);
        String formattedDate = CustomDateTimeFormatter.formatToDate(parsedDate);
        Set<String> attendees = Set.of("PVJ9","7DBB");
        SignupNewEventCommand newEventCommand = new SignupNewEventCommand(
                name,
                date,
                attendees
        );

        CreatedEventDTO expectedResult = new CreatedEventDTO(
                1L,
                name,
                date,
                2
        );

        //When
        when(eventService.createEvent(newEventCommand)).thenReturn(expectedResult);
        mockMvc.perform(post("/api/v1/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newEventCommand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.date").value(formattedDate))
                .andExpect(jsonPath("$.numberOfInvitees").value(2));
    }
}
