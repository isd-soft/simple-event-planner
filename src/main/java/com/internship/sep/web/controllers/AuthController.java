package com.internship.sep.web.controllers;

import com.internship.sep.security.jwt.JwtTokenUtil;
import com.internship.sep.services.UserService;
import com.internship.sep.web.UserDTO;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Getter
    @Setter
    private static class UserEmailPassword {
        String email;
        String password;
    }

    @AllArgsConstructor
    @Getter
    private static class ResponseJwtToken {
        private final String token;
    }

    @Getter
    @Setter
    private static class UserAllFields {
        private String email;
        private String password;
        private String phoneNumber;
        private Integer age;
        private String firstName;
        private String lastName;

        UserDTO toUserDTO() {
            UserDTO dto = new UserDTO();
            dto.setEmail(email);
            dto.setAge(age);
            dto.setPassword(password);
            dto.setPhoneNumber(phoneNumber);
            dto.setLastName(lastName);
            dto.setFirstName(firstName);

            return dto;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEmailPassword request) throws Exception {
        authenticate(request.email, request.password);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email);

        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new ResponseJwtToken(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserAllFields user) throws Exception {
        System.out.println(user);
        user.password = passwordEncoder.encode(user.password);

        UserDTO userDTO = user.toUserDTO();

        userService.addUser(userDTO);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new ResponseJwtToken(token));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User Disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }
    }

}
