package com.internship.sep.mapper;

import com.internship.sep.models.*;
import com.internship.sep.repositories.UserRepository;
import com.internship.sep.web.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventMapper implements Mapper<Event, EventDTO> {

    private final Mapper<Attendee, AttendeeDTO> attendeeMapper;
    private final Mapper<FileDB, FileDTO> attachmentMapper;
    private final Mapper<EventCategory, EventCategoryDTO> eventCategoryMapper;
//    private final Mapper<User, UserDTO> userMapper;

    @Override
    public EventDTO map(Event entity) {

        if (entity == null) {
            return null;
        }

        final EventDTO dto = new EventDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setStartDateTime(entity.getStartDateTime());
        dto.setEndDateTime(entity.getEndDateTime());
        dto.setIsApproved(entity.getIsApproved());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setGoogleEventId(entity.getGoogleEventId());


        if(entity.getHost() != null) {
            User host = entity.getHost();
            UserDTO hostDto = new UserDTO();
            hostDto.setEmail(host.getEmail());
            hostDto.setFirstName(host.getFirstName());
            hostDto.setLastName(host.getLastName());
            hostDto.setId(host.getId());
            hostDto.setAge(host.getAge());
            hostDto.setPhoneNumber(host.getPhoneNumber());
            dto.setHost(hostDto);
        }


        if (entity.getAttendees() != null && entity.getAttendees().size() > 0) {
            dto.setAttendees(attendeeMapper.mapList(entity.getAttendees()));
        }
        if (entity.getEventCategory() != null) {
            EventCategory eventCategory = entity.getEventCategory();
            EventCategoryDTO eventCategoryDTO = new EventCategoryDTO();
            eventCategoryDTO.setName(eventCategory.getName());
            eventCategoryDTO.setId(eventCategory.getId());
            dto.setEventCategory(eventCategoryDTO);
            //dto.setEventCategory(eventCategoryMapper.map(entity.getEventCategory()));
        }

        if (entity.getAttachments() != null && entity.getAttachments().size() > 0){
            dto.setAttachments(entity.getAttachments().stream().map(file -> {
                var _file = new FileDTO();
                _file.setMultipartFile(file.getFile());
                _file.setId(file.getId());
                return _file;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    @Override
    public Event unmap(EventDTO dto) {
        if (dto == null) {
            return null;
        }

        UserDTO host = dto.getHost();
        EventCategoryDTO eventCategoryDTO = dto.getEventCategory();

        final Event event = new Event();

        event.setDescription(dto.getDescription());
        event.setStartDateTime(dto.getStartDateTime());
        event.setEndDateTime(dto.getEndDateTime());
        event.setIsApproved(dto.getIsApproved());
        event.setName(dto.getName());
        event.setLocation(dto.getLocation());


        event.setGoogleEventId(dto.getGoogleEventId());


        dto.getAttendees().stream()
                .map(attendeeMapper::unmap)
                .forEach(event::addAttendee);

        dto.getAttachments().stream().map(_dto -> {
            var file = new FileDB();
            file.setFile(_dto.getMultipartFile());
            return file;
        });

//        if (dto.getEventCategory() != null) {
//            event.setEventCategory(eventCategoryMapper.unmap(dto.getEventCategory()));
//        }

        return event;
    }
}
