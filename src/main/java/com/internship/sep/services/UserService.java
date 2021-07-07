package com.internship.sep.services;

import com.internship.sep.web.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    UserDTO getUserByEmail(String email);

    UserDTO getUserById(Long id);

    void addUser(UserDTO user);

    void updateUser(UserDTO userDTO);

    void deleteUser(Long userId);
}
