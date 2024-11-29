package com.example.TicTacToe.web.model;

public class WebGameField {
    private static final int SIZE = 3;
    private int[][] field;

    public WebGameField() {
        field = new int[SIZE][SIZE];
    }

    public WebGameField(int[][] field) {
        this.field = field;
    }

    public int[][] getField() {
        return field;
    }

    public void setFieldCeil(int i, int j, int value) {
        field[i][j] = value;
    }
}
