package com.internship.sep.web.controllers;

import com.internship.sep.services.UserService;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public void changeUser(@RequestParam UserDTO userDTO) {
        // TODO: Verifica daca utilizatorul poate modifica datele
        userService.updateUser(userDTO);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable Long userId) {
        // TODO: Calea poate fi accesata doar de admini
        userService.deleteUser(userId);
    }
}
