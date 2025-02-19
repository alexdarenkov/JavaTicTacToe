package com.example.TicTacToe.web.controller;

import com.example.TicTacToe.domain.model.User;
import com.example.TicTacToe.domain.service.AuthService;
import com.example.TicTacToe.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpRequest signUpRequest) {
        authService.register(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        JwtResponse jwtResponse = authService.login(authRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/access")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse jwtResponse = authService.updateAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse jwtResponse = authService.updateRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok(jwtResponse);
    }

}
