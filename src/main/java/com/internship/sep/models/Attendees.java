package com.internship.sep.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode
public class Attendees {
    private Long id;
    private String email;
    private Event event;
    public Attendees() {};
    public Attendees(Long id, String email, Event event){
        this.id = id;
        this.email = email;
        this.event = event;
    }
}
