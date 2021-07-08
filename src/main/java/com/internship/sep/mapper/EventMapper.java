package com.internship.sep.mapper;

import com.internship.sep.models.Attendee;
import com.internship.sep.models.Event;
import com.internship.sep.models.User;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class EventMapper implements Mapper<Event, EventDTO> {

    private final Mapper<Attendee, AttendeeDTO> attendeeMapper;

    @Override
    public EventDTO map(Event entity) {

        if (entity == null) {
            return null;
        }

        final EventDTO dto = new EventDTO();
        dto.setId(entity.getId());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        dto.setStartDateTime(entity.getStartDateTime());
        dto.setEndDateTime(entity.getEndDateTime());
        dto.setIsApproved(entity.getIsApproved());
        dto.setName(entity.getName());

        if (entity.getAttendees() != null && entity.getAttendees().size() > 0) {
            attendeeMapper.mapList(entity.getAttendees());
        }

        return dto;
    }

    @Override
    public Event unmap(EventDTO dto) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setId(dto.getId());
        event.setCategory(dto.getCategory());
        event.setDescription(dto.getDescription());
        event.setStartDateTime(dto.getStartDateTime());
        event.setEndDateTime(dto.getEndDateTime());
        event.setIsApproved(dto.getIsApproved());
        event.setName(dto.getName());

        if (dto.getAttendees() != null && dto.getAttendees().size() > 0) {
            attendeeMapper.mapList(dto.getAttendees());

        }

        return event;
    }


}
