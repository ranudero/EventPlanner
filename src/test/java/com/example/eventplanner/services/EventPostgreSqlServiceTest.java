package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.repositories.EventRepository;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Tag("unit-tests")
public class EventPostgreSqlServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private AttendeeService attendeeService;

    @InjectMocks
    private EventPostgreSqlService eventPostgreSqlService;

    private SignupNewEventCommand newEvent;
    private LocalDateTime todayPlusTwoDays;
    private LocalDateTime today;
    private String todayString;
    private String todayPlusTwoDaysString;
    Set<Attendee> mockAttendees;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockAttendees = Set.of(
                new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9")),
                new Attendee("Nick Verbrugghe", new PersonalCode("PVJ8")),
                new Attendee("Sissi Verbrugghe", new PersonalCode("PVJ7"))
        );

        today = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        todayString = CustomDateTimeFormatter.formatToDate(today);
        todayPlusTwoDays = today.plusDays(2).truncatedTo(ChronoUnit.SECONDS);
        todayPlusTwoDaysString = CustomDateTimeFormatter.formatToDate(todayPlusTwoDays);

        Set<String> attendeeCodes = new TreeSet<>(List.of("PVJ9","PVJ8","PVJ7","PVJ9","PVJ8","PVJ7"));

        newEvent = new SignupNewEventCommand(
                "Test Event",
                todayPlusTwoDaysString,
                attendeeCodes);
    }
}
