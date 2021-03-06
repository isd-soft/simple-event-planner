package com.internship.sep.web;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.internship.sep.models.EventReaction;
import com.internship.sep.web.deserializer.LocalDateTimeDeserializer;
import com.internship.sep.web.serializer.LocalDateTimeSerializer;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private String location;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDateTime;
    private String description;
    private String googleEventId;
    private EventCategoryDTO eventCategory;
    private Boolean isApproved;
    private UserDTO host;
    private List <AttendeeDTO> attendees = new ArrayList<>();
    private List<FileDTO> attachments = new ArrayList<>();
    private List<LinkDTO> links = new ArrayList<>();
    private List<CommentDTO> comments = new ArrayList<>();
    private List<EventReactionDTO> eventReactions = new ArrayList<>();
}
