package com.internship.sep.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class User {

    private String email;
    private String firstName;
    private String lastName;
    private Integer age;
    private String phoneNumber;
    private String password;
    private Role role;

    public User() {}

    public User(String email, String firstName, String lastName, Integer age, String phoneNumber, String password, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }
}
