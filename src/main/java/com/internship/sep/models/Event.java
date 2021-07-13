package com.internship.sep.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Builder
@Table(name = "events")
public class Event extends AbstractEntity {

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "location")
    @NotBlank
    private String location;

    @Column(name = "start_date_time")
    @NotBlank
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    @NotBlank
    private LocalDateTime endDateTime;

    @Column(name = "description")
    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_category_id")
    @NotBlank
    private EventCategory eventCategory;

    @Column(name = "google_event_id")
    private String googleEventId;

    @Column(name = "is_approved")
    @NotBlank
    private Boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank
    private User host;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "event" )
    private List<Attendee> attendees = new ArrayList<>();

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
        attendee.setEvent(this);
    }

    public List<Attendee> getAttendees() {
        return Collections.unmodifiableList(attendees);
    }
}

