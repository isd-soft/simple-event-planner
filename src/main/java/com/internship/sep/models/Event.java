package com.internship.sep.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class Event {
    private String name;
    private String location;
    private Date date;
    private String description;
    private String category;

    public Event(String name, String location, Date date, String description, String category) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.category = category;
    }
}
