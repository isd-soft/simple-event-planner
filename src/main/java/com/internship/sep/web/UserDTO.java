package com.internship.sep.web;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Role role = Role.USER;

    private List<EventDTO> hostedEvents = new ArrayList<>();


}
