package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Event;
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

        return eventRepository.findById(id)
                .map(eventMapper::map)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Override
    public EventDTO createNewEvent(EventDTO eventDTO) {
        System.out.println(eventDTO.toString());
        return saveAndReturnDTO(eventMapper.unmap(eventDTO));

    }

    private EventDTO saveAndReturnDTO(Event event) {
        System.out.println(event.toString());
        Event savedEvent = eventRepository.save(event);

        try {
            gEventService.createEvent(event);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return eventMapper.map(savedEvent);
    }

    @Transactional
    @Override
    public EventDTO patchEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        String gId = event.getGoogleEventId();
        eventRepository.deleteById(id);

        event = eventMapper.unmap(eventDTO);
        event.setId(id);
        event.setGoogleEventId(gId);

        try {
            gEventService.updateEvent(event);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        Event savedEvent = eventRepository.save(event);
        return eventMapper.map(savedEvent);
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        eventRepository.deleteById(id);
        log.warn("Event deleted from DB");
    }
}
