package com.internship.sep.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @Column(name = "email",
            nullable = false,
            unique = true
    )

    @Email(message = "not a valid email")
    private String email;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "first name should not be null")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "last name should not be null")
    private String lastName;

    @Column(name = "age", nullable = false)
    @NotNull(message = "age should not be null")
    private Integer age;

    @Column(name = "phone_number", nullable = false)
    @NotBlank
    @Size(min = 9, max = 9, message = "this is not a valid number(should contain 9 numbers)")
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "password should not be null")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    @NotNull(message = "role should not be null")
    private Role role = Role.USER;

    @OneToMany(mappedBy = "host")
    private List<Event> events = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}

