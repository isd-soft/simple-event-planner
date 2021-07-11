package com.internship.sep.mapper;

import com.internship.sep.models.User;
import com.internship.sep.web.UserShortDTO;
import org.springframework.stereotype.Component;

@Component
public class UserShortMapper implements Mapper<User, UserShortDTO> {
    @Override
    public UserShortDTO map(User entity) {
        return UserShortDTO.builder()
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .role(entity.getRole())
                .build();
    }
}
