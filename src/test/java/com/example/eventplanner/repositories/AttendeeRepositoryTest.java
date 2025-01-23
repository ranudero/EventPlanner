package com.example.eventplanner.repositories;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.PersonalCode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration-tests")

public class AttendeeRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private AttendeeRepository repository;

    @Test
    public void testSaveAndFindByCode() {
        //Given
        Attendee attendee = new Attendee(
                "John Doe",
                new PersonalCode("1234")
        );
        repository.save(attendee);

        //When
        Optional<Attendee> foundAttendee = repository.findByCode(attendee.getCode());

        //Then
        assertNotNull(foundAttendee);
        assertThat(foundAttendee).isPresent();
        assertEquals(attendee.getName(),foundAttendee.get().getName());
        assertEquals(attendee.getCode(), foundAttendee.get().getCode());
    }
}
