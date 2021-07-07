package com.internship.sep.web.controllers;

import com.internship.sep.services.EventService;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(EventController.BASE_URL)
public class EventController {

    public static final String BASE_URL = "/events";

    private final EventService eventService;

    @GetMapping
    public List<EventDTO> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping(path = "{eventId}")
    public EventDTO getEvent(@PathVariable("eventId") Long eventId) {
        return eventService.getEventById(eventId);
    }

    @PostMapping
    public void addEvent(@RequestBody EventDTO eventDTO) {
        eventService.createNewEvent(eventDTO);
    }

    @PutMapping(path = "{eventId}")
    public void changeEvent(@PathVariable("eventId") Long eventId,
                            @RequestBody EventDTO eventDTO) {
        eventService.saveEventByDTO(eventDTO);
    }

    @PutMapping(path = "/accept/{eventId}")
    public void acceptEvent(@PathVariable("eventId") Long eventId) {
        // TODO: Cale doar pentru admini
        // TODO: eventService.acceptEvent();
            // Adminul accepta evenimentul
    }

    @DeleteMapping(path = "{eventId}")
    public void deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEventById(eventId);
    }
}
