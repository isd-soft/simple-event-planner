package com.internship.sep.services;

import com.internship.sep.models.Role;
import com.internship.sep.web.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getUsers();

    UserDTO getUserByEmail(String email);

    void addUser(UserDTO user);

    void updateUser(Long userId,
                   String firstName,
                   String lastName,
                   Integer age,
                   String phoneNumber,
                   String email,
                   Role role);

    void deleteUser(Long userId);
}
