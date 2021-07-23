package com.internship.sep.web.controllers;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.services.CommentService;
import com.internship.sep.services.EventService;

import com.internship.sep.web.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping(CommentController.BASE_URL)

public class CommentController {
    public static final String BASE_URL = "/comments";
    public final CommentService commentService;
    public final EventService eventService;
    public final EventRepository eventRepository;

    @PostMapping(path = "/{eventId}")
    public ResponseEntity<?> createComment(@PathVariable("eventId") Long eventId, @RequestBody CommentDTO commentDTO, Principal principal){
        CommentDTO comment = commentService.createNewComment(commentDTO, eventId, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @CrossOrigin("*")
    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId, Principal principal){
        commentService.deleteComment(commentId, principal.getName());
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    @PutMapping(path = "/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId, Principal principal, @RequestBody CommentDTO commentDTO){
        CommentDTO comment = commentService.updateComment(commentId, commentDTO, principal.getName());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

}
