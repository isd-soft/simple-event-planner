package com.internship.sep.web;

import com.internship.sep.models.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserShortDTO {
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
