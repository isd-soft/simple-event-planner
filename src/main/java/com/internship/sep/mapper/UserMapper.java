package com.internship.sep.mapper;

import com.internship.sep.models.Event;
import com.internship.sep.models.User;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class UserMapper implements Mapper<User, UserDTO> {

    private final Mapper<Event, EventDTO> eventMapper;

    @Override
    public UserDTO map(User entity) {

        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAge(entity.getAge());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());


        if (entity.getEvents() != null && entity.getEvents().size() > 0) {
            dto.setHostedEvents(eventMapper.mapList(entity.getEvents()));
        }

        return dto;
    }


}
