package com.example.eventplanner.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime start;
    @ManyToMany
    @JoinTable(
            name = "inviteperevent",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "attendee_id"))
    @Getter (AccessLevel.PACKAGE)
    private Set<Attendee> attendeeList;

    public Event(String name, LocalDateTime start, Set<Attendee> attendeeList) {
        this.name = name;
        this.start = start;
        this.attendeeList = attendeeList;
    }

    public int getNumberOfInvitees() {
        return attendeeList.size();
    }



}
