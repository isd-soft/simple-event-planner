package com.internship.sep.mapper;

import com.internship.sep.models.EventReaction;
import com.internship.sep.web.EventReactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventReactionMapper implements Mapper<EventReaction, EventReactionDTO> {


    private final UserShortMapper userShortMapper;

    @Override
    public EventReactionDTO map(EventReaction entity) {

        if (entity == null) {
            return null;
        }

        EventReactionDTO reactionDTO = new EventReactionDTO();
        reactionDTO.setEventId(entity.getEvent().getId());
        reactionDTO.setId(entity.getId());
        reactionDTO.setCreator(userShortMapper.map(entity.getUser()));
        reactionDTO.setType(entity.getType());

        return reactionDTO;
    }

    @Override
    public EventReaction unmap(EventReactionDTO dto) {

        if (dto == null) {
            return null;
        }

        EventReaction reaction = new EventReaction();
        reaction.setId(dto.getId());
        reaction.setType(dto.getType());

        return reaction;
    }
}
