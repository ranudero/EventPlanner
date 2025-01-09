package com.example.eventplanner.apis;

import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.services.AttendeePostgreSqlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendees")
@RequiredArgsConstructor
public class Controller {
    private final AttendeePostgreSqlService attendeePostgreSqlService;

    @PostMapping
    public CreatedAttendeeDTO addAttendee(@RequestBody @Valid SignupNewAttendeeCommand newAttendee) {
        return attendeePostgreSqlService.addAttendee(newAttendee);
    }
}
