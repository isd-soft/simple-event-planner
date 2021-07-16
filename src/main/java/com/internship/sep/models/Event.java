package com.internship.sep.models;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    // TODO: 14/07/2021 add validation for events when it's finished

    @NotBlank(message = "event title should be present")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "event location must be present")
    @Column(name = "location")
    private String location;

    @NotNull(message = "event start time must be present")
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @NotNull(message = "event end time must be present")
    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "description")
    private String description;

    @NotNull(message = "event category must be present")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_category_id")
    private EventCategory eventCategory;

    @Column(name = "google_event_id")
    private String googleEventId;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User host;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "event" )
    private List<Attendee> attendees = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "event" )
    private List<FileDB> attachments = new ArrayList<>();


    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
        attendee.setEvent(this);
    }

    public List<Attendee> getAttendees() {
        return Collections.unmodifiableList(attendees);
    }

    public void addAttachments(FileDB attachment){
        attachments.add(attachment);
        attachment.setEvent(this);
    }
    public List<FileDB> getAttachments(){
        return  Collections.unmodifiableList(attachments);
    }
}

