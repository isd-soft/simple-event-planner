package com.internship.sep.mapper;

import com.internship.sep.models.User;
import com.internship.sep.web.UserDTO;
import org.springframework.stereotype.Component;

@Component
class UserMapper implements Mapper<User, UserDTO> {

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

        return user;
    }

}
