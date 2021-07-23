package com.internship.sep.web;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.internship.sep.web.serializer.LocalDateTimeSerializer;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private Long eventId;
    private UserShortDTO creator;
    private String content;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creation_date;
    private List<CommentReactionDTO> commentReactions = new ArrayList<>();

}
