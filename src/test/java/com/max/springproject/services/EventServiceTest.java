package com.max.springproject.services;

import com.max.springproject.models.Event;
import com.max.springproject.models.User;
import com.max.springproject.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class EventServiceTest {

    private EventRepository repository = Mockito.mock(EventRepository.class);

    private EventService service = new EventService(repository);

    @Test
    void getById() {
        Event event = new Event(1, "name", new User());
        Mockito.when(repository.getById(1l)).thenReturn(event);
        assertEquals(event, service.getById(1l));
    }

    @Test
    void getAll() {
        List<Event> events = Arrays.asList(new Event(1, "name", new User()), new Event());
        Mockito.when(repository.findAll()).thenReturn(events);
        assertEquals(events, service.getAll());
    }

    @Test
    void save() {
        Event event = new Event(1, "name", new User());
        Mockito.when(repository.save(event)).thenReturn(event);
        assertEquals(event, service.save(event));
    }

    @Test
    void deleteById() {
        repository.deleteById(2l);
        Mockito.verify(repository, Mockito.times(1)).deleteById(2l);
    }
}