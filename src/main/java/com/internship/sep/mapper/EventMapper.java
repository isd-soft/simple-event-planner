package com.internship.sep.mapper;
import com.internship.sep.models.*;
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
    private final Mapper<FileDB, FileDTO> fileMapper;
    private final Mapper <LinkDB, LinkDTO> linkMapper;



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

        if (entity.getLinkDB()!= null && entity.getLinkDB().size()>0){
            dto.setLinks(linkMapper.mapList(entity.getLinks()));
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
            dto.setAttachments(entity.getAttachments().stream()
                    .map(fileMapper::map)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    @Override
    public Event unmap(EventDTO dto) {
        if (dto == null) {
            return null;
        }

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

        dto.getLinks().stream()
                .map(linkMapper::unmap)
                .forEach(event::addLinkDB);

        dto.getAttachments().stream()
                .map(fileMapper::unmap)
                .forEach(event::addAttachment);


        return event;
    }
}
