package com.max.springproject.controllers;

import com.max.springproject.models.Event;
import com.max.springproject.models.User;
import com.max.springproject.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class EventControllerTest {

    private EventService service = Mockito.mock(EventService.class);
    private EventController controller = new EventController(service);

    private Event event = new Event(1, "testName", new User());
    private List<Event> events = Arrays.asList(event);

    @Test
    void getEvents() {
        Mockito.when(service.getAll()).thenReturn(events);
        assertEquals(events.toString(), controller.getEvents());
    }

    @Test
    void getEventById() {
        Mockito.when(service.getById(1l)).thenReturn(event);
        assertEquals(event.toString(), controller.getEventById(1l));
    }

    @Test
    void saveEvent() {
        Mockito.when(service.save(event)).thenReturn(event);
        assertEquals(event.toString(), controller.saveEvent(event));
    }

    @Test
    void deleteEvent() {
        controller.deleteEvent(2l);
        Mockito.verify(service, Mockito.times(1)).deleteById(2l);
    }
}