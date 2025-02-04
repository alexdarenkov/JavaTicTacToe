package com.example.TicTacToe.web.controller;

import com.example.TicTacToe.domain.model.User;
import com.example.TicTacToe.domain.service.AuthService;
import com.example.TicTacToe.web.dto.GameRequest;
import com.example.TicTacToe.web.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpRequest signUpRequest) {
        boolean isRegistered = authService.register(signUpRequest);
        if (isRegistered) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
        UUID userId = authService.login(authHeader);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
