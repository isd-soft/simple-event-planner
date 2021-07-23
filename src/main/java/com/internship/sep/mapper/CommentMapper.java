package com.internship.sep.mapper;
import com.internship.sep.models.Comment;
import com.internship.sep.web.CommentDTO;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
class CommentMapper implements Mapper<Comment, CommentDTO>{

    @Autowired
    UserShortMapper userShortMapper;

    @Override
    public CommentDTO map(Comment entity) {

        if (entity == null) {
            return null;
        }

       CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setEventId(entity.getEvent().getId());
        dto.setCreation_date(entity.getCreationDate());
        dto.setContent(entity.getContent());
        dto.setCreator(userShortMapper.map(entity.getUser()));

        return dto;
    }
    @Synchronized
    @Nullable
    @Override
    public Comment unmap(CommentDTO dto) {

        if (dto == null) {
            return null;
        }

        Comment entity = new Comment();
        entity.setId(dto.getId());
        entity.setCreationDate(LocalDateTime.now());
        entity.setContent(dto.getContent());
        return entity;
    }

}
