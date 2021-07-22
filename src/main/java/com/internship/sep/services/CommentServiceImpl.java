package com.internship.sep.services;


import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Comment;

import com.internship.sep.models.Event;
import com.internship.sep.models.Role;
import com.internship.sep.models.User;
import com.internship.sep.repositories.CommentRepository;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.repositories.UserRepository;
import com.internship.sep.web.CommentDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final Mapper<Comment, CommentDTO> commentMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CommentDTO createNewComment(CommentDTO commentDTO, Long eventId, String creatorEmail){
        Event event = eventRepository.findById(eventId).orElseThrow(ResourceNotFoundException::new);
        Comment comment = commentMapper.unmap(commentDTO);
        User creator = userRepository.findByEmail(creatorEmail).orElseThrow(ResourceNotFoundException::new);
        comment.setUser(creator);
        event.addComment(comment);
        commentRepository.save(comment);
        return commentDTO;
    }

    @Transactional
    @Override
    public void deleteComment(Long id, String creatorEmail){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id: " + id + " does not exists"));
        User creator = userRepository.findByEmail(creatorEmail).orElseThrow(ResourceNotFoundException::new);
        if(!(creator.getEmail().equals(comment.getUser().getEmail())) && !(creator.getRole().equals(Role.ADMIN))) {
            return;
        }
        commentRepository.delete(comment);
    }

    @Transactional
    @Override
    public CommentDTO updateComment(Long id, CommentDTO commentDTO, String creatorEmail){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id: " + id + " does not exists"));

        User creator = userRepository.findByEmail(creatorEmail).orElseThrow(ResourceNotFoundException::new);
        if(!(creator.getEmail().equals(comment.getUser().getEmail())) && !(creator.getRole().equals(Role.ADMIN))) {
            return commentMapper.map(comment);
        }
        comment.setContent(commentDTO.getContent());
        commentRepository.save(comment);
       return commentMapper.map(comment);

    }



}
