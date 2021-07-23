package com.internship.sep.services;

import com.internship.sep.web.CommentReactionDTO;
import com.internship.sep.web.EventReactionDTO;

public interface ReactionService {

    EventReactionDTO setEventReaction(EventReactionDTO reactionDTO, String creatorEmail, Long EventId);
    CommentReactionDTO setCommentReaction(CommentReactionDTO commentReactionDTO, String creatorEmail, Long commentId);

}
