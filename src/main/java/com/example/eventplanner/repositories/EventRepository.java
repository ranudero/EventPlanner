package com.example.eventplanner.repositories;

import com.example.eventplanner.domain.Attendee;
import com.example.eventplanner.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByName(String eventName);
    List<Event> findAllByName(String eventName);

}
