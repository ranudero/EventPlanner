package com.example.eventplanner.repositories;

import com.example.eventplanner.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByName(String eventName);

    @Query(value = "SELECT * FROM Event e WHERE e.name = :name ORDER BY ABS(EXTRACT(EPOCH FROM (e.start::timestamp - CAST(:now AS timestamp)))) ASC LIMIT 1", nativeQuery = true)
    Optional<Event> findClosestEventByName(@Param("name") String name, @Param("now") LocalDateTime now);

}
