package com.internship.sep.web;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
public class EventDTO {

    @NotNull(message = "id must be present")
    private Long id;

    @NotNull(message = "name must be present")
    private String name;

    @NotNull(message = "location must be present")
    private String location;

    @NotNull(message = "start time must be present")
    @FutureOrPresent(message = "start time must be either present or future")
    private LocalDateTime startDateTime;

    @NotNull(message = "end time must be present")
    @Future(message = "end time must be in future")
    private LocalDateTime endDateTime;

    private String description;

    @NotNull(message = "event category must be present")
    private EventCategoryDTO EventCategory;

    @NotNull(message = "approve status must be present")
    private Boolean isApproved;

    @NotNull(message = "host reference must be present")
    private UserDTO host;
    private List<AttendeeDTO> attendees = new ArrayList<>();
}

