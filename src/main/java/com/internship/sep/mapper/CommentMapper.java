package com.internship.sep.mapper;
import com.internship.sep.models.Comment;
import com.internship.sep.models.CommentReaction;
import com.internship.sep.web.CommentDTO;
import com.internship.sep.web.CommentReactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
class CommentMapper implements Mapper<Comment, CommentDTO>{

    private final Mapper<CommentReaction, CommentReactionDTO> commentReactionMapper;


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

        if (entity.getCommentReaction() != null && entity.getCommentReaction().size()>0){
            dto.setCommentReactions(entity.getCommentReactions().stream()
                    .map(commentReactionMapper::map).
                            collect(Collectors.toList()));
        }



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


        dto.getCommentReactions().stream()
                .map(commentReactionMapper::unmap)
                .forEach(entity::addCommentReaction);


        return entity;
    }

}
