package com.internship.sep.services;
import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.*;
import com.internship.sep.repositories.*;
import com.internship.sep.services.googleCalendarAPI.GEventService;
import com.internship.sep.web.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Mapper<Event, EventDTO> eventMapper;
    private final GEventService gEventService;
    private final AttendeeRepository attendeeRepository;
    private final LinkDBRepository linkDBRepository;
    private final UserRepository userRepository;
    private final Mapper<EventCategory, EventCategoryDTO> eventCategoryMapper;
    private final Mapper<Attendee, AttendeeDTO> attendeeMapper;
    private final EventCategoryRepository eventCategoryRepository;
    private final EmailService emailService;
    private final FileDBRepository fileDBRepository;
    private final Mapper<FileDB, FileDTO> fileMapper;
    private final Mapper<LinkDB, LinkDTO> linkMapper;


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
    public EventDTO createNewEvent(EventDTO eventDTO, String hostEmail) throws IOException {
        Event event = eventMapper.unmap(eventDTO);
        User host = userRepository.findByEmail(hostEmail).orElseThrow(ResourceNotFoundException::new);
        try {
            EventCategory eventCategory = eventCategoryRepository.getById(eventDTO.getEventCategory().getId());
            event.setEventCategory(eventCategory);
        } catch (NullPointerException ex) {
            log.error("Null pointer exception, EventCategory object is null");
        }
        event.setIsApproved(false);
        event.setHost(host);

        Event savedEvent = eventRepository.save(event);

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
        oldEvent.setStartDateTime(eventDTO.getStartDateTime().plusHours(3));
        oldEvent.setEndDateTime(eventDTO.getEndDateTime().plusHours(3));
        oldEvent.setLocation(eventDTO.getLocation());
        oldEvent.setDescription(eventDTO.getDescription());
        oldEvent.setEventCategory(eventCategoryMapper.unmap(eventDTO.getEventCategory()));

        oldEvent.getAttendees().forEach(attendeeRepository::delete);

        oldEvent.setAttendees(new ArrayList<>());



        oldEvent.getLinks().forEach(linkDBRepository::delete);

        oldEvent.setLinks(new ArrayList<>());

        eventDTO.getAttendees().stream()
                .map(attendeeMapper::unmap)
                .forEach(oldEvent::addAttendee);

        eventDTO.getLinks().stream()
                .map(linkMapper::unmap)
                .forEach(oldEvent::addLinkDB);

        oldEvent.getAttachments().retainAll(eventDTO.getAttachments()
                .stream()
                .map(fileMapper::unmap)
                .collect(Collectors.toList()));

        eventDTO.getAttachments().stream()
                .map(fileMapper::unmap)
                .forEach(oldEvent::addAttachment);

        eventRepository.save(oldEvent);

        if(oldEvent.getIsApproved()) {
            try {
                gEventService.updateEvent(oldEvent);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                log.warn("Couldn't update event in Google...");
            } catch (IOException e) {
                e.printStackTrace();
                log.warn("Couldn't update event in Google...");
            } catch (RuntimeException e) {
                e.printStackTrace();
                log.warn("Couldn't update event in Google...");
            }
        }

        return eventMapper.map(oldEvent);

    }

    @Transactional
    @Override
    public void approveEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        if(event.getIsApproved() == true) {
            return;
        }

        event.setIsApproved(true);
        eventRepository.save(event);

        try {
            gEventService.createEvent(event);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            log.warn("Couldn't save event to Google...");
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.warn("Couldn't save event to Google...");
        }

        try {
            emailService.sendEmail("Event " + event.getName() + " was approved!", event.getHost().getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Couldn't send the email...");
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
                log.warn("Couldn't delete event in Google...");
            } catch (IOException e) {
                e.printStackTrace();
                log.warn("Couldn't delete event in Google...");
            }
        }

        event.getAttendees().forEach(attendeeRepository::delete);
        event.getLinkDB().forEach(linkDBRepository::delete);

        eventRepository.deleteById(id);

        try {
            emailService.sendEmail("Event " + event.getName() + " was deleted!", event.getHost().getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Couldn't send the email...");
        }
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

    @Transactional
    @Override
    public FileDTO getAttachments(Long id) {

        FileDB file = fileDBRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File with id: " + id + " does not exists"));

        FileDTO fileDTO = fileMapper.map(file);
        fileDTO.setContent(new String(file.getContent(), StandardCharsets.UTF_8));
        return fileDTO;

    }

    @Override
    @Transactional
    public void deleteFileById(Long id) {
        FileDB file = fileDBRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File with id: " + id + " does not exists"));

        fileDBRepository.delete(file);
    }


}