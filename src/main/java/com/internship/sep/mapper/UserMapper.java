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

    @Override
    public User unmap(UserDTO userDTO) {

        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        if (userDTO.getHostedEvents() != null && userDTO.getHostedEvents().size() > 0) {
            user.setEvents(eventMapper.unmapList(userDTO.getHostedEvents()));
        }

        return user;
    }

}
