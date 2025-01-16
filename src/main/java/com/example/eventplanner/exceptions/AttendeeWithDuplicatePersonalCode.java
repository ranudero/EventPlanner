package com.example.eventplanner.exceptions;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import com.example.eventplanner.domain.PersonalCode;

public class AttendeeWithDuplicatePersonalCode extends PSQLException {
    public AttendeeWithDuplicatePersonalCode(PersonalCode personalCode) {
        super("Attendee with personalCode already exists.", PSQLState.UNIQUE_VIOLATION);
    }

}
