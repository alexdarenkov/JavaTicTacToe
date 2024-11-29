package com.example.TicTacToe.domain.service;

import com.example.TicTacToe.domain.model.DomainGame;

public interface ServiceInterface {
    DomainGame getNextMove(DomainGame domainGame);
    String isValidMove(DomainGame domainGame);
    String getGameStatus(DomainGame domainGame);
}
