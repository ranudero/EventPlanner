package com.example.eventplanner.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.comparator.Comparators;

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

    public Attendee(String name, PersonalCode code) {
        this.name = name;
        this.code = code;
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
}
