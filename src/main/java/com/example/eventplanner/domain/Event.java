package com.example.eventplanner.domain;

import com.example.eventplanner.utils.CustomDateTimeFormatter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @Getter
    private Set<Attendee> attendeeList;

    public Event(String name, LocalDateTime start, Set<Attendee> attendeeList) {
        this.name = name;
        this.start = start;
        this.attendeeList = attendeeList;
    }

    public int getNumberOfInvitees() {
        return attendeeList.size();
    }

    public String getStart() {
        return CustomDateTimeFormatter.formatToDate(start);
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", attendeeList=" + attendeeList +
                '}';
    }
}
