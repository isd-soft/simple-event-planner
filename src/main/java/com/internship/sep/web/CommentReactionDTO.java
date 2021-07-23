package com.internship.sep.web;
import com.internship.sep.models.ReactionType;
import lombok.Data;

@Data
public class CommentReactionDTO {

    private Long id;
    private ReactionType type;
    private Long commentId;
    private UserShortDTO creator;
}
