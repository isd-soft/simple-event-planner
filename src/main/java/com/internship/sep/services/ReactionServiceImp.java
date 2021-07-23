package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.*;
import com.internship.sep.repositories.*;
import com.internship.sep.web.CommentReactionDTO;
import com.internship.sep.web.EventReactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionServiceImp implements ReactionService {
    private final Mapper<EventReaction, EventReactionDTO> eventReactionMapper;
    private final Mapper<CommentReaction, CommentReactionDTO> commentReactionMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final EventReactionRepository eventReactionRepository;
    private final CommentReactionRepository commentReactionRepository;

    @Transactional
    @Override
    public EventReactionDTO setEventReaction(EventReactionDTO reactionDTO, String creatorEmail, Long eventId) {
        User user = userRepository.findByEmail(creatorEmail).orElseThrow(ResourceNotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(ResourceNotFoundException::new);
        EventReaction reaction = eventReactionMapper.unmap(reactionDTO);
        reaction.setUser(user);
        reaction.setEvent(event);

        List<EventReaction> allReactions = event.getEventReactions();

        for(int i = 0; i < allReactions.size(); i++) {
            if(allReactions.get(i).getUser().getEmail().equalsIgnoreCase(creatorEmail)) {
                if(allReactions.get(i).getType().equals(reaction.getType())) {
                    allReactions.remove(i);
                } else {
                    allReactions.get(i).setType(reaction.getType());
                    eventReactionRepository.save(allReactions.get(i));
                }
                return eventReactionMapper.map(reaction);
            }
        }

        event.addEventReaction(reaction);
        eventReactionRepository.save(reaction);
        return eventReactionMapper.map(reaction);
    }

    @Transactional
    @Override
    public CommentReactionDTO setCommentReaction(CommentReactionDTO commentReactionDTO, String creatorEmail, Long commentId){
        User user = userRepository.findByEmail(creatorEmail).orElseThrow(ResourceNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(ResourceNotFoundException::new);
        CommentReaction reaction = commentReactionMapper.unmap(commentReactionDTO);
        reaction.setUser(user);
        reaction.setComment(comment);

        comment.getCommentReactions().forEach(commentReaction ->{
            if (commentReaction.getUser().getEmail().equalsIgnoreCase(creatorEmail)){
                if(commentReaction.getType().equals(reaction.getType())){
                    commentReactionRepository.delete(commentReaction);
                }else{
                    commentReaction.setType(reaction.getType());
                    commentReactionRepository.save(reaction);
                }
                return;
            }
        } );

        comment.addCommentReaction(reaction);
        commentReactionRepository.save(reaction);
        return commentReactionMapper.map(reaction);
    }

}
