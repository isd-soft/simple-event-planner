package com.internship.sep.web;
import com.internship.sep.models.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;
    private String phoneNumber;
    private String password;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.age = entity.getAge();
        this.phoneNumber = entity.getPhoneNumber();
        this.password = entity.getPassword();
    }
}
