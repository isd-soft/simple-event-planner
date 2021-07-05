package com.internship.sep.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private String category;

    @ManyToOne
    private User host;
    private Boolean isApproved;

    @OneToMany(mappedBy = "event" )
    private Set<Attendees> attendees;
}
