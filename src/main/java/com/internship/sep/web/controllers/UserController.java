package com.internship.sep.web.controllers;

import com.internship.sep.services.UserService;
import com.internship.sep.web.UserDTO;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @Getter
    @Setter
    private static class UserVisible {
        protected Long id;
        protected String email;
        protected String phoneNumber;
        protected Integer age;
        protected String firstName;
        protected String lastName;

        static UserVisible fromDto(UserDTO userDTO) {
            UserVisible user = new UserVisible();
            user.id = userDTO.getId();
            user.age = userDTO.getAge();
            user.phoneNumber = userDTO.getPhoneNumber();
            user.email = userDTO.getEmail();
            user.firstName = userDTO.getFirstName();
            user.lastName = userDTO.getLastName();

            return user;
        }
    }

    @Getter
    @Setter
    private static class UserAllData extends UserVisible {
        protected String password;

        void resetDTO(UserDTO userDTO) {
            if (email != null) {
                userDTO.setEmail(email);
            }
            if (password != null) {
                userDTO.setPassword(password);
            }
            if (age != null) {
                userDTO.setAge(age);
            }
            if (firstName != null) {
                userDTO.setFirstName(firstName);
            }
            if (lastName != null) {
                userDTO.setLastName(lastName);
            }
            if (phoneNumber != null) {
                userDTO.setPhoneNumber(phoneNumber);
            }
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(
                userService.getUsers()
                    .stream()
                    .map(UserVisible::fromDto)
                    .collect(Collectors.toList())
        );
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(
                UserVisible.fromDto(userService.getUserById(userId)));
    }

    @PutMapping(path = "{userId}")
    public void changeUser(@PathVariable("userId") Long userId,
                           @RequestBody UserAllData user,
                           Principal principal) throws IllegalAccessException {


        System.out.println(principal.getName());
        UserDTO userDTO = userService.getUserByEmail(principal.getName());
        if (!userId.equals(userDTO.getId())) {
            throw new IllegalAccessException("Illegal access");
        }

        user.resetDTO(userDTO);

        userService.updateUser(userDTO);
    }
}
