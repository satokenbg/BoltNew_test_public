package com.circle.schedulemanager.repository;

import com.circle.schedulemanager.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    @Query("SELECT e FROM Event e WHERE e.startDateTime >= :now ORDER BY e.startDateTime ASC")
    List<Event> findUpcomingEvents(LocalDateTime now);
    
    @Query("SELECT e FROM Event e WHERE e.startDateTime < :now ORDER BY e.startDateTime DESC")
    List<Event> findPastEvents(LocalDateTime now);
    
    List<Event> findByTitleContainingIgnoreCase(String title);
}