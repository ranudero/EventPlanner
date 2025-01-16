package com.example.eventplanner.apis;

import com.example.eventplanner.services.EventService;
import org.junit.jupiter.api.Tag;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@Tag("unit-tests")
@WebMvcTest(EventController.class)
public class ValidationExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

}