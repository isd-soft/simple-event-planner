package com.internship.sep.web;

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
    private EventCategoryDTO EventCategory;
    private Boolean isApproved;
    private UserDTO host ;
    private List <AttendeeDTO> attendees = new ArrayList<>();

    }

