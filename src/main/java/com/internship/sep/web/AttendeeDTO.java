package com.internship.sep.web;

import com.internship.sep.models.Event;
import lombok.Data;

@Data
public class AttendeeDTO {
    private Long id;
    private String email;

    private Event event;
}
