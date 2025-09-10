package com.circle.schedulemanager.controller;

import com.circle.schedulemanager.entity.Event;
import com.circle.schedulemanager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private EventService eventService;
    
    @GetMapping("/")
    public String home(Model model) {
        List<Event> upcomingEvents = eventService.findUpcomingEvents();
        model.addAttribute("upcomingEvents", upcomingEvents);
        return "index";
    }
}