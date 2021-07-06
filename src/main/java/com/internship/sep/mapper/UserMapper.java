package com.internship.sep.mapper;

import com.internship.sep.models.User;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;

public class UserMapper {

    public UserDTO userToUserDTO(User entity) {

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAge(entity.getAge());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setPassword(entity.getPassword());

        return dto;
    }
}
