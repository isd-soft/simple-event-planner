package com.internship.sep.services;

import com.internship.sep.models.Role;
import com.internship.sep.models.User;
import com.internship.sep.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void addUser(User user) {
        if (user == null) return;

        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("The email is taken");
        }

        repository.save(user);
    }

    @Transactional
    public void updateUser(Long userId,
                           String firstName,
                           String lastName,
                           Integer age,
                           String phoneNumber,
                           String email,
                           Role role) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id: " + userId + " does not exists"));
        if (firstName != null && !firstName.equals(user.getFirstName())) {
            user.setFirstName(firstName);
        }

        if (lastName != null && !lastName.equals(user.getLastName())) {
            user.setLastName(lastName);
        }

        if (age != null && !age.equals(user.getAge())) {
            user.setAge(age);
        }

        if (phoneNumber != null && !phoneNumber.equals(user.getPhoneNumber())) {
            user.setPhoneNumber(phoneNumber);
        }

        if (email != null && !email.equals(user.getEmail())) {
            user.setEmail(email);
        }

        if (role != null && role != user.getRole()) {
            user.setRole(role);
        }
    }

    public void deleteUser(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id: " + userId + " does not exists"));

        repository.delete(user);
    }
}
