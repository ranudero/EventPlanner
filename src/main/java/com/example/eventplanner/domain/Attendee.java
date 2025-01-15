package com.example.eventplanner.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.comparator.Comparators;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "attendees")
public class Attendee implements Comparable<Attendee> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private PersonalCode code;

    public Attendee(String name, PersonalCode code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public int compareTo(Attendee o) {
        return Comparators.comparable().compare(this.getCode().getCode(), o.getCode().getCode());
    }
}
