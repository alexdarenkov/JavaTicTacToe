package com.example.TicTacToe.domain.service;

import com.example.TicTacToe.datasource.mapper.DataSourceDomainMapper;
import com.example.TicTacToe.datasource.model.DsUser;
import com.example.TicTacToe.datasource.repository.UserRepository;
import com.example.TicTacToe.domain.exceptions.ResourceNotFoundException;
import com.example.TicTacToe.domain.model.Role;
import com.example.TicTacToe.domain.model.User;
import com.example.TicTacToe.web.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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
        user.setCountWin(0);
        user.setCountDraw(0);
        user.setCountLose(0);
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

    public User getUserById(UUID userId) {
        return toDomainUser(userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not founded")));
    }

    public User getUserByLogin(String login) {
        return toDomainUser(userRepository.findByLogin(login).
                orElseThrow(() -> new ResourceNotFoundException("User with login: " + login + " not founded")));
    }

    public void updateUserCount(UUID id, boolean isWin, boolean isDraw, boolean isLose) {
        DsUser dsUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not founded"));
        if (isWin) {
            dsUser.setCountWin(dsUser.getCountWin() + 1);
        } else if (isDraw) {
            dsUser.setCountDraw(dsUser.getCountDraw() + 1);
        } else if (isLose) {
            dsUser.setCountLose(dsUser.getCountLose() + 1);
        }
        userRepository.save(dsUser);
    }

    public List<User> topUsers(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return userRepository.topUsers(pageable)
                .stream()
                .map(DataSourceDomainMapper::toDomainUser )
                .toList();
    }
}