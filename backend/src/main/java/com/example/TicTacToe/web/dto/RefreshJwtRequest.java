package com.example.TicTacToe.web.dto;

import lombok.Data;

@Data
public class RefreshJwtRequest {
    private String refreshToken;
}
