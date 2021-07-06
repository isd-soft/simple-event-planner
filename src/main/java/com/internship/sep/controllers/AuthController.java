package com.internship.sep.controllers;

import com.internship.sep.services.UserService;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping(path = "/register")
    public void registerUser(@RequestBody UserDTO user) {
        userService.addUser(user);
        // TODO: Obtinerea acces [JWT]
    }

    @PostMapping(path = "/login")
    public void login(@RequestParam String email, @RequestParam String password) {
        // TODO: Obtinerea acces [JWT]
    }
}
