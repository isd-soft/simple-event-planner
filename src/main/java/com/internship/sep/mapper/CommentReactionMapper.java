package com.internship.sep.mapper;
import com.internship.sep.models.CommentReaction;
import com.internship.sep.web.CommentReactionDTO;


public class CommentReactionMapper  implements Mapper<CommentReaction, CommentReactionDTO>{

    @Override
    public CommentReactionDTO map(CommentReaction entity) {

        CommentReactionDTO dto = new CommentReactionDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setCommentId(entity.getComment().getId());
        dto.setCreatorId(entity.getUser().getId());

        return dto;
    }

    @Override
    public CommentReaction unmap(CommentReactionDTO dto) {

        if (dto == null) {
            return null;
        }


        CommentReaction commentReaction = new CommentReaction();

        commentReaction.setId(dto.getId());
        commentReaction.setType(dto.getType());



        return commentReaction;
    }

}
