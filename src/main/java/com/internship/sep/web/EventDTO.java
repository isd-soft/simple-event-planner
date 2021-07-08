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
    private UserDTO host ;
    private List <AttendeeDTO> attendees = new ArrayList<>();

    }

