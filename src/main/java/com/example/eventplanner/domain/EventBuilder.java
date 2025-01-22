package com.example.eventplanner.domain;

import com.example.eventplanner.exceptions.DateNotInFutureException;
import com.example.eventplanner.utils.CustomDateTimeFormatter;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class EventBuilder {
    private String name;
    private LocalDateTime start;
    private Set<Attendee> attendeeList;

    public Event build() {
        return new Event(name, start, attendeeList);
    }

    public EventBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EventBuilder withStart(LocalDateTime start) {

        if (!validateDateIsInFuture(start)){
            throw new DateNotInFutureException();
        }
        this.start = start;
        return this;
    }

    private boolean validateDateIsInFuture(LocalDateTime start){
        LocalDate verifyDate = LocalDate.now();
        return verifyDate.isBefore(start.toLocalDate());
    }

    public EventBuilder withStart(String start){
        LocalDateTime parsedStart = CustomDateTimeFormatter.parseToDateTime(start);
        return withStart(parsedStart);
    }

    public EventBuilder withAttendeeList(Set<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
        return this;
    }


}
