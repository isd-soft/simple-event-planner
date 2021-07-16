package com.internship.sep.services;

import com.internship.sep.mapper.Mapper;
import com.internship.sep.models.Event;
import com.internship.sep.models.User;
import com.internship.sep.web.EventDTO;
import com.internship.sep.web.UserDTO;
import com.internship.sep.repositories.UserRepository;
import com.internship.sep.web.UserShortDTO;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.aspectj.bridge.MessageUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final Mapper<User, UserDTO> userMapper;
    private final Mapper<User, UserShortDTO> userShortMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional(readOnly = true)
    public List<UserShortDTO> getUsers() {
        return repository.findAll().stream()
                .map(userShortMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(userMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException("User with email '" + email + "' isn't registered"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return repository.findById(id)
                .map(userMapper::map)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAge(userDTO.getAge());
        user.setRole(userDTO.getRole());

        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userDTO){
        User user = repository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
    }


    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " does not exists"));

        repository.delete(user);
    }

    @Override
    @Transactional
    public void deleteUserByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + email + " does not exists"));

        repository.delete(user);

    }

    public void authenticate(String username, String password) throws InvalidCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new InvalidCredentialsException();
        }
    }
}
