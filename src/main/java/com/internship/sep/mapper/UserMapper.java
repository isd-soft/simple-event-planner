package com.internship.sep.mapper;

import com.internship.sep.models.Event;
import com.internship.sep.models.User;
import com.internship.sep.repositories.EventRepository;
import com.internship.sep.services.EventService;
import com.internship.sep.services.UserService;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
class UserMapper implements Mapper<User, UserDTO> {
    private final EventRepository eventRepository;

    @Override
    public UserDTO map(User entity) {
        
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAge(entity.getAge());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setPassword(entity.getPassword());

        dto.setEventsHostedId(
                entity.getHostedEvents()
                    .stream()
                    .map(Event::getId)
                    .collect(Collectors.toList())
        );

        return dto;
    }

    @Override
    public User unmap(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());

        user.setHostedEvents(
                userDTO.getEventsHostedId()
                    .stream()
                    .map(eventRepository::getById)
                    .collect(Collectors.toList())
        );

        return user;
    }

}
