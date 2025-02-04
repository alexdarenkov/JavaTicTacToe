package com.example.TicTacToe.domain.service;

import com.example.TicTacToe.datasource.model.DsUser;
import com.example.TicTacToe.datasource.repository.UserRepository;
import com.example.TicTacToe.domain.exceptions.ResourceNotFoundException;
import com.example.TicTacToe.domain.model.User;
import com.example.TicTacToe.web.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.UUID;

import static com.example.TicTacToe.datasource.mapper.DataSourceDomainMapper.toDataSourceUser;
import static com.example.TicTacToe.datasource.mapper.DataSourceDomainMapper.toDomainUser;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(SignUpRequest signUpRequest) {
        if (userRepository.findByLogin(signUpRequest.getLogin()).isPresent()) {
            return false;
        }
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin(signUpRequest.getLogin());
        user.setPassword(signUpRequest.getPassword());
        userRepository.save(toDataSourceUser(user));
        return true;
    }

    public UUID authenticateUser(String login, String password) {
        Optional<DsUser> dsUser = userRepository.findByLogin(login);

        if (!dsUser.isPresent()) {
            return null;
        }

        User user = toDomainUser(dsUser.get());
        if (user.getPassword().equals(password)) {
            return user.getId();
        }

        return null; // Пользователь не найден или пароль неверный
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

    public User getUserInfo(UUID userId) {
        return toDomainUser(userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not founded")));
    }
}