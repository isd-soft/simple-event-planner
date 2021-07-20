package com.internship.sep.web.controllers;
import com.internship.sep.models.FileDB;
import com.internship.sep.models.User;
import com.internship.sep.repositories.UserRepository;
import com.internship.sep.services.EventService;
import com.internship.sep.services.ResourceNotFoundException;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.FileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(EventController.BASE_URL)
public class EventController {

    public static final String BASE_URL = "/events";

    private final EventService eventService;
    private final UserRepository userRepository;

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
    public ResponseEntity<String> createEvent(@RequestBody EventDTO eventDTO, Principal principal) throws IOException {
        eventService.createNewEvent(eventDTO, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Event created successfully");
    }

    @CrossOrigin("*")
    @PutMapping(path = "/{eventId}")
    public ResponseEntity<String> updateEvent(@PathVariable("eventId") Long eventId,
                                            @RequestBody EventDTO eventDTO, Principal principal) {

        System.out.println(eventDTO);
        eventService.updateEvent(eventId, eventDTO, principal.getName());
        return new ResponseEntity<>("Event updated successfully", HttpStatus.OK);
    }

    @PutMapping(path = "/approve-event/{eventId}")
    public ResponseEntity<String> approveEvent(@PathVariable("eventId") Long eventId) {

        eventService.approveEventById(eventId);
        return new ResponseEntity<>("Event approved successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable("eventId") Long eventId, Principal principal) {

        eventService.deleteEventById(eventId, principal.getName());
        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
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
    @GetMapping(path = "files/{id}")
    public ResponseEntity<FileDTO> getFiles(@PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.getAttachments(id));
    }

    @DeleteMapping(path = "/files/{id}")
    public ResponseEntity<?> deleteFileById(@PathVariable("id") Long id) {
       eventService.deleteFileById(id);
        return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
    }

}


