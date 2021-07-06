package com.internship.sep.mapper;

import com.internship.sep.models.Event;
import com.internship.sep.models.User;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;

public class EventMapper implements Mapper<Event, EventDTO> {
    @Override
    public EventDTO map(Event entity) {
        EventDTO dto = new EventDTO();
        dto.setId(entity.getId());
        dto.setAttendees(entity.getAttendees());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        dto.setStartDateTime(entity.getStartDateTime());
        dto.setEndDateTime(entity.getEndDateTime());
        dto.setIsApproved(entity.getIsApproved());
        dto.setName(entity.getName());

        return dto;
    }
    @Override
    public Event unmap(EventDTO dto) {

        Event event = new Event();
        event.setId(dto.getId());
        event.setAttendees(dto.getAttendees());
        event.setCategory(dto.getCategory());
        event.setDescription(dto.getDescription());
        event.setStartDateTime(dto.getStartDateTime());
        event.setEndDateTime(dto.getEndDateTime());
        event.setIsApproved(dto.getIsApproved());
        event.setName(dto.getName());

        return event;
    }


}
