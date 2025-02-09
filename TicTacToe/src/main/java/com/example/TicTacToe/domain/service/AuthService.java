package com.example.TicTacToe.domain.service;

import com.example.TicTacToe.domain.model.User;
import com.example.TicTacToe.web.dto.JwtRequest;
import com.example.TicTacToe.web.dto.JwtResponse;
import com.example.TicTacToe.web.dto.SignUpRequest;
import com.example.TicTacToe.web.security.JwtProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    // TODO разобраться с refreshStorage
    private final Map<UUID, String> refreshStorage = new HashMap<>();

    public void register(SignUpRequest signUpRequest) {
        userService.registerUser(signUpRequest);
    }

    public JwtResponse login(JwtRequest jwtRequest) {
        User user = userService.authenticateUser(jwtRequest.getLogin(), jwtRequest.getPassword());
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getId(), refreshToken);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(accessToken);
        jwtResponse.setRefreshToken(refreshToken);
        return jwtResponse;
    }

    public JwtResponse updateAccessToken(String refreshToken) {
        return updateToken(refreshToken, false);
    }

    public JwtResponse updateRefreshToken(String refreshToken) {
        return updateToken(refreshToken, true);
    }

    private JwtResponse updateToken(String refreshToken, boolean updateRefreshToken) {
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        UUID userId = UUID.fromString(claims.getSubject());
        String saveRefreshToken = refreshStorage.get(userId);
        if (!refreshToken.equals(saveRefreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        User user = userService.getUserById(userId);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(jwtProvider.generateAccessToken(user));
        if (updateRefreshToken) {
            refreshStorage.put(userId, refreshToken);
            jwtResponse.setRefreshToken(jwtProvider.generateRefreshToken(user));
        } else {
            jwtResponse.setRefreshToken(refreshToken);
        }
        return jwtResponse;
    }
}
