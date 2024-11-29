package com.example.TicTacToe.datasource.model;

public class DataSourceGameField {
    private static final int SIZE = 3;
    private int[][] field;

    public DataSourceGameField() {
        field = new int[SIZE][SIZE];
    }

    public DataSourceGameField(int[][] field) {
        this.field = field;
    }

    public int[][] getField() {
        return field;
    }

    public void setFieldCeil(int i, int j, int value) {
        field[i][j] = value;
    }
}
