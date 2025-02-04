package com.example.TicTacToe.domain.exceptions;

public class GameRequestException extends RuntimeException {
    public GameRequestException(String message) {
        super(message);
    }
}
