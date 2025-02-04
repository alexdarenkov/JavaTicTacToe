package com.example.TicTacToe.web.mapper;

import com.example.TicTacToe.domain.model.Game;
import com.example.TicTacToe.web.dto.GameResponse;

public class WebDomainMapper {
    private static GameResponse gameResponse;

    public static GameResponse toResponse(Game game) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.setId(game.getId());
        gameResponse.setField(game.getField());
        gameResponse.setGameState(game.getGameState());
        return gameResponse;
    }
}
