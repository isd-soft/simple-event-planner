package com.internship.sep.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "event_categories")
public class EventCategory extends AbstractEntity {

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "eventCategory")
    private List<Event> events = new ArrayList<>();
}
