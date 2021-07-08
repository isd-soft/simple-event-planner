package com.internship.sep.web.controllers;

import com.internship.sep.services.UserService;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{userId}")
    public UserDTO getUser(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping(path = "{userId}")
    public void changeUser(@PathVariable("userId") Long userId,
                           @RequestBody UserDTO userDTO,
                           Principal principal) throws IllegalAccessException {

        if (userDTO.getId() == null) {
            userDTO.setId(userId);
        }

        System.out.println(principal.getName());
        UserDTO checkUserDTO = userService.getUserByEmail(principal.getName());
        if (!userId.equals(checkUserDTO.getId())) {
            throw new IllegalAccessException("Illegal access");
        }

        userService.updateUser(userDTO);
    }
}
