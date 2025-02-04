package com.example.TicTacToe.domain.model;

import com.example.TicTacToe.web.dto.GameState;
import lombok.Data;

import java.util.UUID;

@Data
public class Game {
    private UUID id;
    private int[][] field;
    private UUID firstPlayer;
    private UUID secondPlayer;
    private boolean playWithAi;
    private GameState gameState;
}
