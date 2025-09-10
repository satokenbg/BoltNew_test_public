package com.circle.schedulemanager.service;

import com.circle.schedulemanager.entity.Event;
import com.circle.schedulemanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    public List<Event> findAll() {
        return eventRepository.findAll();
    }
    
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }
    
    public List<Event> findUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now());
    }
    
    public List<Event> findPastEvents() {
        return eventRepository.findPastEvents(LocalDateTime.now());
    }
    
    public List<Event> searchByTitle(String title) {
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public Event save(Event event) {
        if (event.getId() == null) {
            event.setCreatedAt(LocalDateTime.now());
        }
        return eventRepository.save(event);
    }
    
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}