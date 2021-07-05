package com.internship.sep.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Attendee {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String email;
    @ManyToOne
    private Event event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendee attendees = (Attendee) o;
        return id != null && id.equals(attendees.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Attendees{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", event=" + event +
                '}';
    }
}
