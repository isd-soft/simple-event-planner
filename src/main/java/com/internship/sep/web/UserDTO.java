package com.internship.sep.web;

import com.internship.sep.models.Event;
import com.internship.sep.models.Role;
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
    private Role role;

    private List<EventDTO> hostedEvents = new ArrayList<>();
}
