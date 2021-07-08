package com.internship.sep.mapper;

import com.internship.sep.models.Attendee;
import com.internship.sep.models.Event;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class AttendeeMapper implements Mapper<Attendee, AttendeeDTO> {

    private final Mapper<Event, EventDTO> eventMapper;

    @Synchronized
    @Nullable
    @Override
    public AttendeeDTO map(Attendee entity) {

        if (entity == null) {
            return null;
        }

        AttendeeDTO dto = new AttendeeDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setEvent(eventMapper.map(entity.getEvent()));
        return dto;
    }

    @Synchronized
    @Nullable
    @Override
    public Attendee unmap(AttendeeDTO dto) {

        if (dto == null) {
            return null;
        }

        Attendee entity = new Attendee();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setEvent(eventMapper.unmap(dto.getEvent()));

        return entity;
    }
}
