package com.internship.sep.services;

import com.internship.sep.models.Event;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.web.EventDTO;

import java.util.List;

public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return null;
    }

    @Override
    public EventDTO getEventByName(String name) {
        return null;
    }
}
