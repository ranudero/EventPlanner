package com.example.eventplanner.apis;

import com.example.eventplanner.dtos.CreatedAttendeeDTO;
import com.example.eventplanner.dtos.SignupNewAttendeeCommand;
import com.example.eventplanner.services.AttendeePostgreSqlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Controller {
    private final AttendeePostgreSqlService attendeePostgreSqlService;

    public Controller(AttendeePostgreSqlService attendeePostgreSqlService) {
        this.attendeePostgreSqlService = attendeePostgreSqlService;
    }

    @PostMapping("/api/v1/attendees")
    public CreatedAttendeeDTO addAttendee(@RequestBody SignupNewAttendeeCommand newAttendee) {
        return attendeePostgreSqlService.addAttendee(newAttendee);
    }
}
