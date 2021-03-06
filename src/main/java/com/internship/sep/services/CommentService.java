package com.internship.sep.services;

import com.internship.sep.web.CommentDTO;


public interface CommentService {
    CommentDTO createNewComment(CommentDTO commentDTO, Long eventId, String creatorEmail);
    void deleteComment(Long id, String creatorEmail);
    CommentDTO updateComment(Long id, CommentDTO commentDTO, String creatorEmail);
}
