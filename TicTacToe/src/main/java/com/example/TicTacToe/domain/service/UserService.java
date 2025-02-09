package com.example.TicTacToe.domain.service;

import com.example.TicTacToe.datasource.model.DsUser;
import com.example.TicTacToe.datasource.repository.UserRepository;
import com.example.TicTacToe.domain.exceptions.ResourceNotFoundException;
import com.example.TicTacToe.domain.model.Role;
import com.example.TicTacToe.domain.model.User;
import com.example.TicTacToe.web.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.example.TicTacToe.datasource.mapper.DataSourceDomainMapper.toDataSourceUser;
import static com.example.TicTacToe.datasource.mapper.DataSourceDomainMapper.toDomainUser;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(SignUpRequest signUpRequest) {
        if (userRepository.findByLogin(signUpRequest.getLogin()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin(signUpRequest.getLogin());
        user.setPassword(signUpRequest.getPassword());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(toDataSourceUser(user));
    }

    public User authenticateUser(String login, String password) {
        DsUser dsUser = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User with login: " + login + " not found"));

        User user = toDomainUser(dsUser);
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new ResourceNotFoundException("Wrong password");
        }
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new RuntimeException("User not authenticated");
    }

    public UUID getUserId(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User with login: " + login + " not found"))
                .getId();
    }

    public User getUserById(UUID userId) {
        return toDomainUser(userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not founded")));
    }
}