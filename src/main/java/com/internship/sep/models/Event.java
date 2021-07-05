package com.internship.sep.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "events")
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
    private Boolean isApproved;

    @ManyToOne
    private User host;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event" )
    private Set<Attendee> attendees = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id != null && id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", host=" + host +
                ", isApproved=" + isApproved +
                ", attendees=" + attendees +
                '}';
    }
}
