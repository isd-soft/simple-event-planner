package com.internship.sep.services;


import com.internship.sep.web.EventDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public interface EventService {

    List<EventDTO> getAllEvents();

    EventDTO getEventByName(String name);

    EventDTO getEventById(Long id);

    EventDTO createNewEvent(EventDTO eventDTO, String hostEmail);

    EventDTO saveEventByDTO(Long id, EventDTO eventDTO);

    EventDTO updateEvent(Long id ,EventDTO eventDTO);

    void deleteEventById(Long id);

//    void saveImage(MultipartFile imageFile) throws Exception;
}


