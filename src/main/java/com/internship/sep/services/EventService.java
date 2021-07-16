package com.internship.sep.services;
import com.internship.sep.models.FileDB;
import com.internship.sep.models.User;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
public interface EventService {

    List<EventDTO> getAllEvents();

    EventDTO getEventByName(String name);

    EventDTO getEventById(Long id);

    EventDTO createNewEvent(EventDTO eventDTO, String hostEmail) throws IOException;

    EventDTO saveEventByDTO(Long id, EventDTO eventDTO);

    EventDTO updateEvent(Long id ,EventDTO eventDTO);

    void deleteEventById(Long id);
    void approveEventById(Long id);

    List<EventDTO> getUnapprovedEvents();

    List<EventDTO> getApprovedEvents();

    List<EventDTO> getMyEvents(User host);

}


