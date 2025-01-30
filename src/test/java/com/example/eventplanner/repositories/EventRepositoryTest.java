package com.example.eventplanner.repositories;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.PersonalCode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration-tests")
public class EventRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    EventRepository repository;

    @Test
    public void findClosestEventByNameTest(){
        //Given
        String name = "Test event";
        LocalDateTime dateEventOne = LocalDateTime.now();
        LocalDateTime dateEventTwo = LocalDateTime.now().plusDays(12);
        Attendee lander = new Attendee("Lander Verbrugghe",new PersonalCode("PVJ9"));
        Attendee nick = new Attendee("Nick Bauters", new PersonalCode("7DBB"));
        Set<Attendee> listOfAttendees = Set.of(lander, nick);
        Event eventOne = new Event(name,dateEventOne,listOfAttendees);
        Event eventTwo = new Event(name, dateEventTwo,listOfAttendees);

        repository.save(eventOne);
        repository.save(eventTwo);

        //When
        Optional<Event> foundEvent = repository.findClosestEventByName(name,LocalDateTime.now());

        //Then
        assertNotNull(foundEvent);
        assertThat(foundEvent).isPresent();
        assertEquals(eventOne.getName(),foundEvent.get().getName());
        assertEquals(eventOne.getStart(),foundEvent.get().getStart());

    }
}
