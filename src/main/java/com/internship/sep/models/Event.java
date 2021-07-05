package com.internship.sep.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Event")

public class Event {

    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private String category;
    private User host;
    private Boolean isApproved;

    @OneToMany
    @JoinColumn
    private Set<Attendees> attendees;

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
