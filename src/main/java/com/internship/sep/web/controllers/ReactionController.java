package com.internship.sep.web.controllers;

import com.internship.sep.repositories.EventRepository;
import com.internship.sep.services.CommentService;
import com.internship.sep.services.EventService;
import com.internship.sep.services.ReactionService;
import com.internship.sep.web.CommentDTO;
import com.internship.sep.web.CommentReactionDTO;
import com.internship.sep.web.EventReactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping(ReactionController.BASE_URL)
public class ReactionController {
    public static final String BASE_URL = "/reactions";
    public final ReactionService reactionService;
    public final EventService eventService;
    public final EventRepository eventRepository;


    @PostMapping(path = "/events/{eventId}")
    public ResponseEntity<EventReactionDTO> setEventReaction(@PathVariable("eventId") Long eventId, @RequestBody EventReactionDTO eventReactionDTO, Principal principal){

        EventReactionDTO reaction = reactionService.setEventReaction(eventReactionDTO, principal.getName(), eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reaction);
    }

    @PostMapping(path = "/comments/{commentId}")
    public ResponseEntity<CommentReactionDTO> setCommentReaction(@PathVariable("commentId") Long commentId, @RequestBody CommentReactionDTO commentReactionDTO, Principal principal){
        CommentReactionDTO reaction = reactionService.setCommentReaction(commentReactionDTO, principal.getName(), commentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reaction);
    }


}

