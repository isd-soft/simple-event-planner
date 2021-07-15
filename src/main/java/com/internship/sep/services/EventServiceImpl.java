package com.internship.sep.services;
import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.*;
import com.internship.sep.repositories.*;

import com.internship.sep.repositories.UserRepository;
import com.internship.sep.services.googleCalendarAPI.GEventService;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.EventCategoryDTO;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Mapper<Event, EventDTO> eventMapper;
    private final GEventService gEventService;
    private final AttendeeRepository attendeeRepository;
    private final UserRepository userRepository;
    private final Mapper<EventCategory, EventCategoryDTO> eventCategoryMapper;
    private final Mapper<Attendee, AttendeeDTO> attendeeMapper;
    private final EventCategoryRepository eventCategoryRepository;


    @Transactional
    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository
                .findAll()
                .stream()
                .map(eventMapper::map)
                .collect(Collectors.toList());

    }

    @Transactional
    @Override
    public EventDTO getEventByName(String name) {
        return eventMapper.map(eventRepository.findByName(name).orElseThrow(ResourceNotFoundException::new));
    }

    @Transactional
    @Override
    public EventDTO getEventById(Long id) {

        Event event = eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        log.info("Started Event Attendees Update...");
        if(event.getIsApproved()) {
            try {
                gEventService.updateAttendeesStatus(event);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return eventRepository.findById(id)
                .map(eventMapper::map)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Override
    public EventDTO createNewEvent(EventDTO eventDTO, String hostEmail) {
        Event event = eventMapper.unmap(eventDTO);
        User host = userRepository.findByEmail(hostEmail).orElseThrow(ResourceNotFoundException::new);
        EventCategory eventCategory = eventCategoryRepository.getById(eventDTO.getEventCategory().getId());
        event.setIsApproved(false);
        event.setHost(host);
        event.setEventCategory(eventCategory);
        Event savedEvent = eventRepository.save(event);

//

        EventDTO returnDto = eventMapper.map(savedEvent);

        return returnDto;

    }

    private EventDTO saveAndReturnDTO(Event event) {
        log.warn("Event saved to DB");
        Event savedEvent = eventRepository.save(event);

        EventDTO returnDto = eventMapper.map(savedEvent);

        return returnDto;
    }

    @Transactional
    @Override
    public EventDTO saveEventByDTO(Long id, EventDTO eventDTO) {
        Event event = eventMapper.unmap(eventDTO);
        event.setId(id);

        return saveAndReturnDTO(event);
    }

    @Transactional
    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO, String hostEmail) {
        Event oldEvent = eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        User principal = userRepository.findByEmail(hostEmail).orElseThrow(ResourceNotFoundException::new);

        if(!(principal.getEmail().equals(oldEvent.getHost().getEmail())) && !(principal.getRole().equals(Role.ADMIN))) {
            return eventMapper.map(oldEvent);
        }

        oldEvent.setName(eventDTO.getName());
        oldEvent.setStartDateTime(eventDTO.getStartDateTime());
        oldEvent.setEndDateTime(eventDTO.getEndDateTime());
        oldEvent.setLocation(eventDTO.getLocation());
        oldEvent.setDescription(eventDTO.getDescription());
        oldEvent.setEventCategory(eventCategoryMapper.unmap(eventDTO.getEventCategory()));

        oldEvent.getAttendees().forEach(attendee -> {
            attendeeRepository.delete(attendee);
        });

        oldEvent.setAttendees(new ArrayList<Attendee>());

        eventDTO.getAttendees().stream()
                .map(attendeeMapper::unmap)
                .forEach(oldEvent::addAttendee);

        // eventRepository.save(oldEvent);


        if(oldEvent.getIsApproved()) {
            try {
                gEventService.updateEvent(oldEvent);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        return eventMapper.map(oldEvent);

    }

    @Transactional
    @Override
    public void approveEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        event.setIsApproved(true);
        eventRepository.save(event);

        log.info("Try to save to google...");
        try {
            gEventService.createEvent(event);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Transactional
    @Override
    public void deleteEventById(Long id, String hostEmail) {
        Event event = eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        User principal = userRepository.findByEmail(hostEmail).orElseThrow(ResourceNotFoundException::new);

        if(!(principal.getEmail().equals(event.getHost().getEmail())) && !(principal.getRole().equals(Role.ADMIN))) {
            return;
        }

        if(event.getIsApproved()) {
            try {
                gEventService.deleteEvent(event.getGoogleEventId());
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        event.getAttendees().forEach(attendee -> {
            attendeeRepository.delete(attendee);
        });

        eventRepository.deleteById(id);
        log.warn("Event deleted from DB");
    }

    @Transactional
    @Override
    public List<EventDTO> getUnapprovedEvents() {
        return eventRepository.findUnapprovedEvents().stream()
                .map(eventMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<EventDTO> getApprovedEvents() {
        return eventRepository.findApprovedEvents().stream()
                .map(eventMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<EventDTO> getMyEvents(User host) {
        return eventRepository.findEventsByHost(host).stream()
                .map(eventMapper::map)
                .collect(Collectors.toList());
    }

}