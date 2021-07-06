package com.internship.sep.services;

import com.internship.sep.models.Role;
import com.internship.sep.web.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getUsers();

    UserDTO getUserByEmail(String email);

    UserDTO getUserById(Long id);

    void addUser(UserDTO user);

    void updateUser(UserDTO userDTO);

    void deleteUser(Long userId);
}
