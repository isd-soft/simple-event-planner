package com.internship.sep.web.controllers;

import com.internship.sep.services.UserService;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(
              userService.getUsers()
        );
    }

    @GetMapping(path = "/full")
    public ResponseEntity<?> getUser(Principal principal) {
        return ResponseEntity.ok(
                userService.getUserByEmail(principal.getName())
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserDTO updatedUser, Principal principal) {
        userService.updateUser(updatedUser);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
