package com.example.TicTacToe.domain.model;

public class DomainGameField {
    private static final int SIZE = 3;
    private int[][] field;

    public DomainGameField() {
        field = new int[SIZE][SIZE];
    }

    public DomainGameField(int[][] field) {
        this.field = field;
    }

    public int[][] getField() {
        return field;
    }

    public void setFieldCeil(int i, int j, int value) {
        field[i][j] = value;
    }
}
