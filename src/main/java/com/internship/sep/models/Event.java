package com.internship.sep.models;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
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
    @Future(message = "event end time must be in the past")
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "event")
    private List<Attendee> attendees = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "event", orphanRemoval = true)
    private List<FileDB> attachments = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "event")
    private List<LinkDB> links = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "event")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "event")
    private List<EventReaction> eventReactions = new ArrayList<>();

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
        attendee.setEvent(this);
    }

    public List<Attendee> getAttendees() {
        return Collections.unmodifiableList(attendees);
    }

    public void addAttachment(FileDB attachment){
        attachments.add(attachment);
        attachment.setEvent(this);
    }


    public void  addLinkDB (LinkDB linkDB){
        links.add(linkDB);
        linkDB.setEvent(this);
    }

    public List<LinkDB> getLinkDB(){
        return Collections.unmodifiableList(links);
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setEvent(this);
    }
    public List<Comment> getComment(){
        return Collections.unmodifiableList(comments);
    }

    public void addEventReaction(EventReaction reaction){
        eventReactions.add(reaction);
        reaction.setEvent(this);
    }
    public List<EventReaction> getEventReaction(){
        return Collections.unmodifiableList(eventReactions);
    }

    @AssertTrue(message = "event end date time must be after start time")
    private boolean isValidEndDateTime() {
        return endDateTime.isAfter(startDateTime);
    }
}

