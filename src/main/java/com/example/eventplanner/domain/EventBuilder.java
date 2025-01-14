package com.example.eventplanner.domain;

import com.example.eventplanner.utils.CustomDateTimeFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
        //TODO add validation for date
        this.start = start;
        return this;
    }

    public EventBuilder withStart(String start){
        this.start = CustomDateTimeFormatter.parseToDateTime(start);
        return this;
    }

    public EventBuilder withAttendeeList(Set<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
        return this;
    }

}
