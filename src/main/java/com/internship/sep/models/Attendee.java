package com.internship.sep.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendees")
public class Attendee {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Getter @Setter private String email;
    @ManyToOne
    @Getter @Setter private Event event;

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
