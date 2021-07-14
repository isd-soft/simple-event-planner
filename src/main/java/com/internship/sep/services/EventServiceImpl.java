package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Event;
import com.internship.sep.repositories.AttendeeRepository;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.services.googleCalendarAPI.GEventService;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Mapper<Event, EventDTO> eventMapper;
    private final GEventService gEventService;
    private final AttendeeRepository attendeeRepository;

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
    public EventDTO createNewEvent(EventDTO eventDTO) {
        Event event = eventMapper.unmap(eventDTO);
        event.setIsApproved(false);
        Event savedEvent = eventRepository.save(event);

        try {
            gEventService.createEvent(event);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

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
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event oldEvent = eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        String gId = oldEvent.getGoogleEventId();
        oldEvent.getAttendees().forEach(attendee -> {
            attendeeRepository.delete(attendee);
        });

        Event updatedEvent = eventMapper.unmap(eventDTO);
        updatedEvent.setId(id);
        updatedEvent.setGoogleEventId(gId);

        eventRepository.save(updatedEvent);

        try {
            gEventService.updateEvent(updatedEvent);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return eventMapper.map(updatedEvent);

    }

    @Transactional
    @Override
    public void deleteEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        try {
            gEventService.deleteEvent(event.getGoogleEventId());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        event.getAttendees().forEach(attendee -> {
            attendeeRepository.delete(attendee);
        });

        eventRepository.deleteById(id);
        log.warn("Event deleted from DB");
    }
}