package com.internship.sep.services;


import com.internship.sep.web.EventDTO;

import java.util.List;

public interface EventService {

    List<EventDTO> getAllEvents();

    EventDTO getEventByName(String name);

    EventDTO getEventById(Long id);

    EventDTO createNewEvent(EventDTO eventDTO);

    EventDTO  saveEventByDTO ( EventDTO  eventDTO);

    EventDTO  patchEvent ( EventDTO  eventDTO);

    void deleteEventById(Long id);
}
