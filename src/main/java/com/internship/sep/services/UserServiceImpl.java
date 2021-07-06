package com.internship.sep.services;

import com.internship.sep.mapper.UserMapper;
import com.internship.sep.models.Role;
import com.internship.sep.models.User;
import com.internship.sep.web.UserDTO;
import com.internship.sep.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserDTO> getUsers() {
        return repository.findAll().stream()
                .map(UserMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserMapper::userToUserDTO)
                .orElseThrow(() -> new IllegalStateException("User with email '" + email + "' isn't registered"));
    }

    @Override
    public void addUser(UserDTO user) {
        repository.save(UserMapper.userDTOToUser(user));
    }

    @Override
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

    @Override
    public void deleteUser(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id: " + userId + " does not exists"));

        repository.delete(user);
    }
}
