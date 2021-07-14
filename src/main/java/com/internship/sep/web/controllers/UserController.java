package com.internship.sep.web.controllers;

import com.internship.sep.services.UserService;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
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

    @PutMapping
    public void updateUser(@RequestBody UserDTO updatedUser, Principal principal) {
        userService.updateUser(updatedUser);
    }

    @DeleteMapping(path = "/{email}")
     public ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping(path = "/{userId}")
//    public ResponseEntity<Void> deleteUserById(@PathVariable("userID") Long userId) {
//        userService.deleteUser(userId);
//        return ResponseEntity.noContent().build();
//    }


}
