package com.internship.sep.services;


import com.internship.sep.web.EventDTO;

import javax.validation.Valid;
import java.util.List;

public interface EventService {

    List<EventDTO> getAllEvents();

    EventDTO getEventByName(String name);

    EventDTO getEventById(Long id);

    EventDTO createNewEvent(EventDTO eventDTO);

    EventDTO saveEventByDTO(Long id, EventDTO eventDTO);

    EventDTO patchEvent(Long id ,EventDTO eventDTO);

    void deleteEventById(Long id);
}
