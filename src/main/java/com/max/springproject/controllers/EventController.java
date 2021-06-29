package com.max.springproject.controllers;

import com.max.springproject.models.Event;
import com.max.springproject.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public String getEvents() {
        return service.getAll().toString();
    }

    @GetMapping("/{id}")
    public String getEventById(@PathVariable("id") long id){
        return service.getById(id).toString();
    }

    @PostMapping
    public String saveEvent(@ModelAttribute("event") Event event) {
        return service.save(event).toString();
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable("id") long id) {
        service.deleteById(id);
        return service.getAll().toString();
    }
}