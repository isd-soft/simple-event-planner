package com.internship.sep.services;

import com.internship.sep.web.UserDTO;
import com.internship.sep.web.UserShortDTO;
import org.apache.http.auth.InvalidCredentialsException;

import java.util.List;

public interface UserService {
    List<UserShortDTO> getUsers();

    UserDTO getUserByEmail(String email);

    UserDTO getUserById(Long id);

    void addUser(UserDTO user);

    void updateUser(UserDTO userDTO);

    void deleteUser(Long userId);

    void authenticate(String email, String password) throws InvalidCredentialsException;

    void deleteUserByEmail(String email);



}
