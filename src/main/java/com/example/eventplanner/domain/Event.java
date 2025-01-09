package com.example.eventplanner.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime start;
    @ManyToMany(mappedBy="inviteperevent")
    private List<Attendee> attendeeList;

    public Event(String name, LocalDateTime start, List<Attendee> attendeeList) {
        this.name = name;
        this.start = start;
        this.attendeeList = attendeeList;
    }

    public String getDate() {
        return start.toString();
    }
}
