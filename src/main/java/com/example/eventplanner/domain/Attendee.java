package com.example.eventplanner.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.comparator.Comparators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "attendees")
public class Attendee implements Comparable<Attendee> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    @Column(unique = true)
    private PersonalCode code;
    @ManyToMany(mappedBy = "attendeeList")
    private List<Event> events = new ArrayList<>();

    public Attendee(String name, PersonalCode code, List<Event> events) {
        this.name = name;
        this.code = code;
        this.events = events;
    }

    public Attendee(String name, PersonalCode code) {
        this(name, code, new ArrayList<>());
    }

    @Override
    public int compareTo(Attendee o) {
        return Comparators.comparable().compare(this.getCode().getCode(), o.getCode().getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendee attendee = (Attendee) o;
        return Objects.equals(code, attendee.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public PersonalCode getPersonalCode() {
        return code;
    }

    public void subscribeToEvent(Event event) {
        events.add(event);
    }

}
