package com.example.TicTacToe.web.mapper;

import com.example.TicTacToe.domain.model.DomainGame;
import com.example.TicTacToe.domain.model.DomainGameField;
import com.example.TicTacToe.web.model.WebGame;
import com.example.TicTacToe.web.model.WebGameField;

public class WebDomainMapper {
    public static DomainGameField toDomainGameField(WebGameField webGameField) {
        return new DomainGameField(webGameField.getField());
    }

    public static WebGameField toWebGameField(DomainGameField domainGameField) {
        return new WebGameField(domainGameField.getField());
    }

    public static DomainGame toDomainGame(WebGame webGame) {
        return new DomainGame(webGame.getId(), toDomainGameField(webGame.getWebGameField()));
    }

    public static WebGame toWebGame(DomainGame domainGame) {
        return new WebGame(domainGame.getId(), toWebGameField(domainGame.getGameField()));
    }
}
