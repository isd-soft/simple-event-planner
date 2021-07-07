package com.internship.sep.security.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
This class is required for storing the username and password we recieve from the client.
 */
@NoArgsConstructor
@Data
@ToString
public class JwtRequest implements Serializable {
    private String email;
    private String password;
}
