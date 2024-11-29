package com.example.TicTacToe.web.model;

import java.util.UUID;

public class WebGame {
    private UUID id;
    private WebGameField webGameField;
    private String errorMessage;
    private String gameStatus;

    public WebGame(UUID id, WebGameField webGameField){
        this.id = id;
        this.webGameField = webGameField;
    }

    public WebGame(UUID id){
        this.id = id;
        this.webGameField = new WebGameField();
    }

    public UUID getId() {
        return id;
    }

    public WebGameField getWebGameField() {
        return webGameField;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getGameStatus() {
        return gameStatus;
    }
}
