package com.internship.sep.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


/*
This is class is required for creating a response containing the JWT to be returned to the user.
 */
@RequiredArgsConstructor
@Getter
public class JwtResponse implements Serializable {
    private final String token;
}
