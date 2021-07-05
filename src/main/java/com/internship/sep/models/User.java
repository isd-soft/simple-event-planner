package com.internship.sep.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;

    private String lastName;

    private Integer age;

    private String phoneNumber;

    private String password;

    private Role role;

}
