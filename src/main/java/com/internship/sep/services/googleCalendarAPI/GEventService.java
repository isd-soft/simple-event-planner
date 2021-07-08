package com.internship.sep.services.googleCalendarAPI;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Value;

public class GEventService {

    Calendar service;
    @Value("${sep.google.calendar.id}")
    String calendarId;

    @Value("${sep.google.calendar.api.key}")
    String apiKey;

    private GEventMapper eventMapper;

    public void createEvent(com.internship.sep.models.Event sepEvent) {
        Event event = eventMapper.map(sepEvent);
    }
}