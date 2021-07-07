package com.internship.sep.services;

import com.internship.sep.mapper.EventMapper;
import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Event;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Mapper <Event, EventDTO> eventMapper;

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
        return eventMapper.map(eventRepository.findByName(name).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public EventDTO getEventById(Long id) {

        return eventRepository.findById(id)
                .map(eventMapper::map)
                .orElseThrow(ResourceNotFoundException::new);


    }

    @Override
    public EventDTO createNewEvent(EventDTO eventDTO) {
        return saveAndReturnDTO(eventMapper.unmap(eventDTO));

    }
    private EventDTO saveAndReturnDTO(Event event) {
        Event savedEvent = eventRepository.save(event);

        EventDTO returnDto = eventMapper.map(savedEvent);

        return returnDto;
    }
    @Override
    public EventDTO saveEventByDTO(EventDTO eventDTO) {
        Event event = eventMapper.unmap(eventDTO);

        return saveAndReturnDTO(event);
    }

    @Override
    public EventDTO patchEvent(EventDTO eventDTO) {
        return eventRepository.findById(eventDTO.getId()).map(event-> {
            if(eventDTO.getName() != null ){
                event.setName(eventDTO.getName());
            }
            EventDTO returnDto = eventMapper.map(eventRepository.save(event));

            return returnDto;
        })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);

    }
}
