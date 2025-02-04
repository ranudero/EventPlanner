package com.example.eventplanner.apis;

import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.RetrievedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.exceptions.AttendeeWithPersonalCodeNotFoundException;
import com.example.eventplanner.services.AttendeePostgreSqlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttendeeController.class)
@Tag("integration-tests")
public class AttendeeControllerTest {
    @MockBean
    AttendeePostgreSqlService attendeeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addAttendeeTest_happyFlow() throws Exception{
        //Given
        SignupNewAttendeeCommand newAttendeeCommand = new SignupNewAttendeeCommand(
                "John Doe",
                "1234"
        );

        CreatedAttendeeDTO expectedResult = new CreatedAttendeeDTO(
                "John Doe",
                "1234"
        );

        //When
        when(attendeeService.addAttendee(newAttendeeCommand)).thenReturn(expectedResult);
        mockMvc.perform(post("/api/v1/attendees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAttendeeCommand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedResult.name()))
                .andExpect(jsonPath("$.code").value(expectedResult.code()));
    }

    @Test
    void getAttendeeByPersonalCodeTest_happyFlow() throws Exception{
        // Given
        String personalCode = "1234";
        List<Event> invitedEvents = Arrays.asList(
                new Event("Event 1", LocalDateTime.now(), Set.of()),
                new Event("Event 2", LocalDateTime.now(), Set.of())
        );
        RetrievedAttendeeDTO expectedResult = new RetrievedAttendeeDTO(
                personalCode,
                "John Doe",
                invitedEvents
        );

        // When
        when(attendeeService.fetchAttendee(personalCode)).thenReturn(expectedResult);

        // Then
        mockMvc.perform(get("/api/v1/attendees/{personalCode}", personalCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalCode").value(expectedResult.personalCode()))
                .andExpect(jsonPath("$.attendeeName").value(expectedResult.attendeeName()))
                .andExpect(jsonPath("$.invitedEvents[0].name").value(invitedEvents.get(0).getName()))
                .andExpect(jsonPath("$.invitedEvents[1].name").value(invitedEvents.get(1).getName()));
    }

    @Test
    void getAttendeeByPersonalCodeTest_notFound() throws Exception{
        // Given
        String personalCode = "1234";

        // When
        when(attendeeService.fetchAttendee(personalCode)).thenThrow(new AttendeeWithPersonalCodeNotFoundException(new PersonalCode(personalCode)));

        // Then
        mockMvc.perform(get("/api/v1/attendees/{personalCode}", personalCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
