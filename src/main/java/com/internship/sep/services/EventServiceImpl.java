package com.internship.sep.services;

import com.internship.sep.mapper.EventMapper;
import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Event;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.web.EventDTO;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.w3c.dom.events.EventException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Mapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository
                .findAll()
                .stream()
                .map(eventMapper::map)
                .collect(Collectors.toList());

    }

    @Override
    public EventDTO getEventByName(String name) {
        return eventMapper.map(eventRepository.findByName(name));
    }

    @Override
    public EventDTO getEventById(Long id) {

        return eventRepository.findById()
                .map(eventMapper::map)
                .orElseThrow(() -> throw )

    }

    @Override
    public EventDTO createNewEvent(EventDTO eventDTO) {
        return ;
    }
    private EventDTO saveAndReturnDTO(Event event) {
        Event savedEvent = eventRepository.save(event);

        EventDTO returnDto = eventMapper.map(savedEvent);

        return returnDto;
    }
    @Override
    public EventDTO saveEventByDTO(Long id, EventDTO eventDTO) {
        return null;
    }

    @Override
    public EventDTO patchEvent(Long id, EventDTO eventDTO) {
        return null;
    }

    @Override
    public void deleteEventById(Long id) {

    }
}
