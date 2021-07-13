package com.internship.sep.mapper;
import com.internship.sep.models.Attendee;
import com.internship.sep.models.Event;
import com.internship.sep.models.EventCategory;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.EventCategoryDTO;
import com.internship.sep.web.EventDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventMapper implements Mapper<Event, EventDTO> {

    private final Mapper<Attendee, AttendeeDTO> attendeeMapper;
    private final Mapper<EventCategory,EventCategoryDTO> eventCategoryMapper;

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

        if (entity.getAttendees() != null && entity.getAttendees().size() > 0) {
            dto.setAttendees(attendeeMapper.mapList(entity.getAttendees()));
        }
        if (entity.getEventCategory() != null) {
            dto.setEventCategory(eventCategoryMapper.map(entity.getEventCategory()));
        }

        return dto;
    }

    @Override
    public Event unmap(EventDTO dto) {
        if (dto == null) {
            return null;
        }

        final Event event = new Event();

        event.setId(dto.getId());
        event.setDescription(dto.getDescription());
        event.setStartDateTime(dto.getStartDateTime());
        event.setEndDateTime(dto.getEndDateTime());
        event.setIsApproved(dto.getIsApproved());
        event.setName(dto.getName());
        event.setLocation(dto.getLocation());

        dto.getAttendees().stream()
                .map(attendeeMapper::unmap)
                .forEach(event::addAttendee);

        if (dto.getEventCategory() != null) {
            event.setEventCategory(eventCategoryMapper.unmap(dto.getEventCategory()));
        }

        return event;
    }


}
