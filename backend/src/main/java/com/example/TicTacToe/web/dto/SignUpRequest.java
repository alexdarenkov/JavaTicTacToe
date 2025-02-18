package com.example.TicTacToe.web.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String login;
    private String password;
}