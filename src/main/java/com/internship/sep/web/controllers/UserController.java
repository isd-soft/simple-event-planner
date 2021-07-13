package com.internship.sep.web.controllers;

import com.internship.sep.services.UserService;
import com.internship.sep.web.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public void changeUser(@RequestBody @Valid UserDTO user, Principal principal) {
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        UserDTO initialUserDTO = userService.getUserByEmail(principal.getName());

        initialUserDTO.setPassword(user.getPassword());
        initialUserDTO.setLastName(user.getLastName());
        initialUserDTO.setFirstName(user.getFirstName());
        initialUserDTO.setAge(user.getAge());
        initialUserDTO.setPhoneNumber(user.getPhoneNumber());
        initialUserDTO.setEmail(user.getEmail());

        userService.updateUser(initialUserDTO);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> handleMethodArgumentNotValid(
//
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(exception.getMessage());
//    }
}
