package com.internship.sep.web.controllers;

import com.internship.sep.services.EventService;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody EventDTO eventDTO) {
        eventService.createNewEvent(eventDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<Void> updateEvent(@PathVariable("eventId") Long eventId,
                                            @RequestBody EventDTO eventDTO) {
        eventService.saveEventByDTO(eventId, eventDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.noContent().build();

    }

}
