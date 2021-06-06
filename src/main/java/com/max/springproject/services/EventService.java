package com.max.springproject.services;

import com.max.springproject.models.Event;
import com.max.springproject.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements GenericService<Event> {
    EventRepository repository;

    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }


    @Override
    public Event getById(long id) {
        return repository.getById(id);
    }

    @Override
    public List<Event> getAll() {
        return repository.findAll();
    }

    @Override
    public Event save(Event object) {
        return repository.save(object);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

}
