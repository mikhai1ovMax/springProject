package com.max.springproject.controllers;

import com.max.springproject.models.Event;
import com.max.springproject.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("/events")
    public String getEvents() {
        return service.getAll().toString();
    }

    @GetMapping("/event/{id}")
    public String getUserById(@PathVariable("id") long id){
        return service.getById(id).toString();
    }

    @PostMapping("/events")
    public String saveEvent(@ModelAttribute("event") Event event) {
        service.save(event);
        return service.getAll().toString();
    }

    @DeleteMapping("/events/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        service.deleteById(id);
        return service.getAll().toString();
    }
}