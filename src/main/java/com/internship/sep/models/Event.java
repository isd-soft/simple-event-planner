package com.internship.sep.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class Event {

    private Long id;
    private String name;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private String category;
    private User host;
    private Boolean isApproved;

    public Event() {}

    public Event(String name, String location, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, String category, User host) {
        this.name = name;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.category = category;
        this.host = host;
        this.isApproved = false;
    }
}
