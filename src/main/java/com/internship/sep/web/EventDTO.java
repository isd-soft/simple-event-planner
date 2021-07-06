package com.internship.sep.web;

import com.internship.sep.models.Attendee;
import com.internship.sep.models.Event;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private String category;
    private Boolean isApproved;

    private List <Attendee> attendees = new ArrayList<>();

    public EventDTO(Event entity) {
        this.id = entity.getId();
        this.attendees = entity.getAttendees();
        this.category = entity.getCategory();
        this.description = entity.getDescription();
        this.startDateTime = entity.getStartDateTime();
        this.endDateTime = entity.getEndDateTime();
        this.isApproved = entity.getIsApproved();
        this.name = entity.getName();
    }

}
