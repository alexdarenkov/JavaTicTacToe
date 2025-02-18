package com.example.TicTacToe.web.dto;

import lombok.Data;

@Data
public class GameRequest {
    private int row;
    private int col;
}
