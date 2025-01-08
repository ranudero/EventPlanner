package com.example.eventplanner.domain;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "attendees")
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;

    public Attendee(String name, PersonalCode code) {
        this.name = name;
        this.code = code.getCode();
    }

}
