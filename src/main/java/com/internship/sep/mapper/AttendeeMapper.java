package com.internship.sep.mapper;

import com.internship.sep.models.Attendee;
import com.internship.sep.models.Event;
import com.internship.sep.web.AttendeeDTO;
import com.internship.sep.web.EventDTO;
import lombok.Synchronized;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
class AttendeeMapper implements Mapper<Attendee, AttendeeDTO> {

    @Override
    public AttendeeDTO map(Attendee entity) {

        if (entity == null) {
            return null;
        }

        AttendeeDTO dto = new AttendeeDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());

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


        return entity;
    }
}
