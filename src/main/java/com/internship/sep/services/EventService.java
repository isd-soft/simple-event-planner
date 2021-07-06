package com.internship.sep.services;


import com.internship.sep.web.EventDTO;

import java.util.List;

public interface EventService {

    List<EventDTO> getAllEvents();

    EventDTO getEventByName(String name);
}
