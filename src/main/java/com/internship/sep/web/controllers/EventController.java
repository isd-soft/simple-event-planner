package com.internship.sep.web.controllers;
import com.internship.sep.services.AttendeeService;
import com.internship.sep.services.EventService;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(EventController.BASE_URL)
public class EventController {

    public static final String BASE_URL = "/events";

    private final EventService eventService;


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

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/uploadImage")
//    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) throws Exception {
//        String returnValue = "start";
//        try{eventService.saveImage(imageFile);
//        } catch (Exception e){
//            e.printStackTrace();
//            returnValue = "error";
//        }
//
//        return returnValue;
//    }

}
