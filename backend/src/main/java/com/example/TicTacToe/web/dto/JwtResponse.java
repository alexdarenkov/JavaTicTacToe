package com.example.TicTacToe.web.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}