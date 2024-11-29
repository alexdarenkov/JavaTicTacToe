package com.example.TicTacToe.domain.model;

import com.example.TicTacToe.web.model.WebGameField;

import java.util.UUID;

public class DomainGame {
    private UUID id;
    private DomainGameField domainGameField;

    public DomainGame(UUID id) {
        this.id = id;
        this.domainGameField = new DomainGameField();
    }

    public DomainGame(UUID id, DomainGameField domainGameField) {
        this.id = id;
        this.domainGameField = domainGameField;
    }

    public DomainGame(UUID id, int[][] field) {
        this.id = id;
        this.domainGameField = new DomainGameField(field);
    }

    public UUID getId() {
        return id;
    }

    public DomainGameField getGameField() {
        return domainGameField;
    }

    public int[][] getField() {
        return domainGameField.getField();
    }

    public void setGameFieldCeil(int i, int j, int value) {
        domainGameField.setFieldCeil(i, j, value);
    }
}
