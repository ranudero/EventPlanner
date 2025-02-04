package com.example.eventplanner.services;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import com.example.eventplanner.dtos.AttendeeDTO;
import com.example.eventplanner.dtos.RetrievedEventDTO;
import com.example.eventplanner.dtos.SignupNewEventCommand;
import com.example.eventplanner.exceptions.EventWithNameNotFoundException;
import com.example.eventplanner.repositories.EventRepository;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @Nested
    class FetchEventUseCase {

        @Test
        @DisplayName("Test is fetchEvent returns the correct Event information")
        void testFetchEvent_happyFlow(){

            //Given
            String eventNameOne = "Test event";
            LocalDateTime startEventOne = LocalDateTime.now();
            Set<Attendee> attendeeListOne = Set.of(
                    new Attendee("Lander Verbrugghe", new PersonalCode("PVJ9")),
                    new Attendee("Nick Bauters",new PersonalCode("7DBB"))
            );
            Event repositoryEventOne = new Event(eventNameOne,startEventOne,attendeeListOne);

            String eventNameTwo = "Test event";
            LocalDateTime startEventTwo = LocalDateTime.now().plusDays(7);
            Set<Attendee> attendeeListTwo = Set.of(
                    new Attendee("John Doe", new PersonalCode("AABB")),
                    new Attendee("Rafa Nadal", new PersonalCode("BBCC")),
                    new Attendee("Roger Federer", new PersonalCode("CCDD")
                    )
            );
            Event repositoryEventTwo = new Event(eventNameTwo,startEventTwo,attendeeListTwo);

            RetrievedEventDTO expectedResult = new RetrievedEventDTO(
                    "Test event",
                    CustomDateTimeFormatter.formatToDate(startEventOne),
                    attendeeListOne.size(),
                    attendeeListOne.stream().map(AttendeeDTO::from).collect(Collectors.toSet())
            );

            //when
            when(eventRepository.findClosestEventByName(any(),any())).thenReturn(Optional.of(repositoryEventOne));
            RetrievedEventDTO actualEvent = eventPostgreSqlService.fetchEvent(eventNameOne);

            //then
            assertNotNull(actualEvent);
            assertEquals(expectedResult.name(),actualEvent.name());
            assertEquals(expectedResult.startDate(), actualEvent.startDate());
            assertEquals(expectedResult.numberOfInvitees(),actualEvent.numberOfInvitees());
            assertEquals(expectedResult.attendeeList(), actualEvent.attendeeList());
        }

        @Test
        @DisplayName("Given event name not known then EventWithNameNotFoundException thrown")
        void testEventNameNotKnow_unHappyPath(){
            when(eventRepository.findClosestEventByName(any(),any())).thenReturn(Optional.empty());
            assertThrows(EventWithNameNotFoundException.class,()-> eventPostgreSqlService.fetchEvent("Feest"));
        }
    }
}
