package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.User;
import com.internship.sep.web.UserDTO;
import com.internship.sep.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final Mapper<User, UserDTO> mapper;

    @Override
    public List<UserDTO> getUsers() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::map)
                .orElseThrow(() -> new IllegalStateException("User with email '" + email + "' isn't registered"));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return mapper.map(repository.getById(id));
    }

    @Override
    public void addUser(UserDTO userDTO) {
        repository.save(mapper.unmap(userDTO));
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) {
        User user = repository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalStateException("User with id: " + userDTO.getId() + " does not exists"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
    }

    @Override
    public void deleteUser(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id: " + userId + " does not exists"));

        repository.delete(user);
    }
}
