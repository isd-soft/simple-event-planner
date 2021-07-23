package com.internship.sep.services;

import com.internship.sep.web.EventReactionDTO;

public interface ReactionService {

    void setEventReaction(EventReactionDTO reactionDTO, String creatorEmail, Long EventId);

}
