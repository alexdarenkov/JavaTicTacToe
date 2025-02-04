package com.example.TicTacToe.domain.service;

import com.example.TicTacToe.web.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    public boolean register(SignUpRequest signUpRequest) {
        return userService.registerUser(signUpRequest);
    }

    public UUID login(String authHeader) {
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded);
        final String[] values = credentials.split(":", 2);
        if (values.length == 2) {
            return userService.authenticateUser(values[0], values[1]);
        }
        return null;
    }
}
