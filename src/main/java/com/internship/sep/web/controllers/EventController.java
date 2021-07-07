package com.internship.sep.web.controllers;

import com.internship.sep.services.EventService;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(EventController.BASE_URL)
public class EventController {

    public static final String BASE_URL = "/events";

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventDTO> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping(path = "/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDTO getEvent(@PathVariable("eventId") Long eventId) {
        return eventService.getEventById(eventId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody EventDTO eventDTO) {
        eventService.createNewEvent(eventDTO);
    }

    @PutMapping(path = "/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(@PathVariable("eventId") Long eventId,
                            @RequestBody EventDTO eventDTO) {
        eventService.saveEventByDTO(eventDTO);
    }

    @PutMapping(path = "/accept/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptEvent(@PathVariable("eventId") Long eventId) {
        // TODO: Cale doar pentru admini
        // TODO: eventService.acceptEvent();
            // Adminul accepta evenimentul
    }

    @DeleteMapping(path = "{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEventById(eventId);
    }

    @PatchMapping(path = "/{eventId}")
    public ResponseEntity<EventDTO> patchEvent(@PathVariable("eventId") Long eventId,
                                              @RequestBody EventDTO eventDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.patchEvent(eventDTO));
        //return eventService.patchEvent(eventDTO);
    }
}
