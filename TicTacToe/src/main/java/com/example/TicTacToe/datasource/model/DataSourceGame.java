package com.example.TicTacToe.datasource.model;

import com.example.TicTacToe.domain.model.DomainGameField;

import java.util.UUID;

public class DataSourceGame {
    private UUID id;
    private DataSourceGameField dataSourceGameField;

    public DataSourceGame(UUID id) {
        this.id = id;
        this.dataSourceGameField = new DataSourceGameField();
    }

    public DataSourceGame(UUID id, DataSourceGameField dataSourceGameField) {
        this.id = id;
        this.dataSourceGameField = dataSourceGameField;
    }

    public DataSourceGame(UUID id, int[][] field) {
        this.id = id;
        this.dataSourceGameField = new DataSourceGameField(field);
    }

    public UUID getId() {
        return id;
    }

    public DataSourceGameField getGameField() {
        return dataSourceGameField;
    }

    public int[][] getField() {
        return dataSourceGameField.getField();
    }

    public void setGameFieldCeil(int i, int j, int value) {
        dataSourceGameField.setFieldCeil(i, j, value);
    }
}
