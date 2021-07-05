package com.internship.sep.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Attendees {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String email;
    @ManyToOne
    private Event event;

}
