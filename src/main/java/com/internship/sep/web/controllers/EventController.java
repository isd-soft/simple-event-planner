package com.internship.sep.web.controllers;
import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Event;
import com.internship.sep.models.User;
import com.internship.sep.repositories.UserRepository;
import com.internship.sep.services.EventService;
import com.internship.sep.services.ResourceNotFoundException;
import com.internship.sep.services.UserService;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping(EventController.BASE_URL)
public class EventController {

    public static final String BASE_URL = "/events";

    private final EventService eventService;
    private final UserRepository userRepository;
    private final Mapper<Event, EventDTO> eventMapper;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody EventDTO eventDTO, Principal principal) {
        eventService.createNewEvent(eventDTO, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<Void> updateEvent(@PathVariable("eventId") Long eventId,
                                            @RequestBody EventDTO eventDTO) {

        eventService.updateEvent(eventId, eventDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/approve-event/{eventId}")
    public ResponseEntity<Void> updateEvent(@PathVariable("eventId") Long eventId,
                                            Principal principal) {

        eventService.approveEventById(eventId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/unapproved")
    public ResponseEntity<List<EventDTO>> getUnapprovedEvents() {
        return ResponseEntity.ok(eventService.getUnapprovedEvents());
    }
    @GetMapping(path = "/approved")
    public ResponseEntity<List<EventDTO>> getApprovedEvents() {
        return ResponseEntity.ok(eventService.getApprovedEvents());
    }
    @GetMapping(path = "/my-events")
    public ResponseEntity<List<EventDTO>> getMyEvents(Principal principal) {
        String hostEmail = principal.getName();
        User host = userRepository.findByEmail(hostEmail).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(eventService.getMyEvents(host));
    }
}


