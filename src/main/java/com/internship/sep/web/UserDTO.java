package com.internship.sep.web;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;
    private String phoneNumber;
    private String password;

    private List<Long> eventsHostedId = new ArrayList<>();
}
