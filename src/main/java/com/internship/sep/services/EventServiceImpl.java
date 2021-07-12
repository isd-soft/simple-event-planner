package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Event;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Mapper<Event, EventDTO> eventMapper;

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
        return saveAndReturnDTO(eventMapper.unmap(eventDTO));

    }

    private EventDTO saveAndReturnDTO(Event event) {
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
    public EventDTO patchEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        event.setEventCategory(event.getEventCategory());

        EventDTO returnDto = eventMapper.map(eventRepository.save(event));

        return returnDto;

    }

    @Transactional
    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);

    }
}
