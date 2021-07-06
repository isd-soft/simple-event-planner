package com.internship.sep.mapper;

import com.internship.sep.models.Attendee;
import com.internship.sep.web.AttendeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class AttendeeMapper implements Mapper<Attendee, AttendeeDTO> {

    @Override
    public AttendeeDTO map(Attendee entity) {
        AttendeeDTO dto = new AttendeeDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());

        return dto;
    }

    @Override
    public Attendee unmap(AttendeeDTO dto) {
        Attendee entity = new Attendee();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());

        return entity;
    }
}
