package com.example.TicTacToe.web.dto;

import com.example.TicTacToe.domain.model.GameState;
import lombok.Data;

import java.util.UUID;

@Data
public class GameResponse {
    private UUID id;
    private int[][] field;
    private GameState gameState;
}
