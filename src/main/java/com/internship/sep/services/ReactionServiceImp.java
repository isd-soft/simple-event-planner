package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Event;
import com.internship.sep.models.EventReaction;
import com.internship.sep.models.User;
import com.internship.sep.repositories.CommentRepository;
import com.internship.sep.repositories.EventReactionRepository;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.repositories.UserRepository;
import com.internship.sep.web.EventReactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionServiceImp implements ReactionService {
    private final Mapper<EventReaction, EventReactionDTO> eventReactionMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final EventReactionRepository eventReactionRepository;

    @Transactional
    @Override
    public void setEventReaction(EventReactionDTO reactionDTO, String creatorEmail, Long eventId) {
        User user = userRepository.findByEmail(creatorEmail).orElseThrow(ResourceNotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(ResourceNotFoundException::new);
        EventReaction reaction = eventReactionMapper.unmap(reactionDTO);
        reaction.setUser(user);
        reaction.setEvent(event);

        event.getEventReaction().forEach(eventReaction -> {
            if(eventReaction.getUser().getEmail().equalsIgnoreCase(creatorEmail)) {
                if(eventReaction.getType().equals(reaction.getType())) {
                    eventReactionRepository.delete(eventReaction);
                } else {
                    eventReaction.setType(reaction.getType());
                    eventReactionRepository.save(eventReaction);
                }
                return;
            }
        });


        event.addEventReaction(reaction);
        eventReactionRepository.save(reaction);
        return;
    }
}
