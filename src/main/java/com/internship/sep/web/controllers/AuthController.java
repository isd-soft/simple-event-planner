package com.internship.sep.web.controllers;

import com.internship.sep.security.jwt.JwtTokenUtil;
import com.internship.sep.services.UserDetailsServiceImpl;
import com.internship.sep.services.UserService;
import com.internship.sep.web.LoginDTO;
import com.internship.sep.web.UserDTO;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) throws Exception {
        userService.authenticate(login.getEmail(), login.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok()
                .header("Authorization", token)
                .header("Access-Control-Expose-Headers", "Authorization")
                .header("Access-Control-Allow-Headers", "Authorization")
                .body("Logged successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
