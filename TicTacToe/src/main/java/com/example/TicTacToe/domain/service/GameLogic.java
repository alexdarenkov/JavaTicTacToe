package com.example.TicTacToe.domain.service;

import java.util.Arrays;

public final class GameLogic {
    private static final int SIZE = 3;
    private static final int X = 1;
    private static final int O = 2;
    private static final int WEIGHT = 10;
    private static final int PLAYER = X;
    private static final int COMPUTER = O;

    public static boolean isDraw(int[][] field) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isWin(int[][] field, int player) {
        for (int i = 0; i < SIZE; i++) {
            if (field[i][0] == player && field[i][1] == player && field[i][2] == player) {
                return true;
            }
        }

        for (int i = 0; i < SIZE; i++) {
            if (field[0][i] == player && field[1][i] == player && field[2][i] == player) {
                return true;
            }
        }

        if (field[0][0] == player && field[1][1] == player && field[2][2] == player) {
            return true;
        }

        if (field[0][2] == player && field[1][1] == player && field[2][0] == player) {
            return true;
        }

        return false;
    }


    static int minimax(int[][] field, int currPlayer)
    {
        if (isWin(field, COMPUTER)) return WEIGHT;
        if (isWin(field, PLAYER)) return -WEIGHT;
        if (isDraw(field)) return 0;

        // Ход компьютера
        if (currPlayer == COMPUTER) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (field[i][j] == 0) {
                        field[i][j] = COMPUTER;
                        best = Math.max(best, minimax(field, PLAYER));
                        field[i][j] = 0;
                    }
                }
            }

            return best;
        }
        // Ход игрока
        else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (field[i][j] == 0) {
                        field[i][j] = PLAYER;
                        best = Math.min(best, minimax(field, COMPUTER));
                        field[i][j] = 0;
                    }
                }
            }

            return best;
        }
    }

    // Находим самый выгодный ход для компьютера
    static int[] findBestMove(int[][] field)
    {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == 0) {
                    field[i][j] = COMPUTER;
                    int currScore = minimax(field, PLAYER);
                    field[i][j] = 0;

                    if (currScore > bestScore) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestScore = currScore;
                    }
                }
            }
        }

        return bestMove;
    }


    // Проверяем что первый ход игрока правильный
    public static boolean checkFirstMove(int[][] field) {
        long countOfZeros = Arrays.stream(field)
                .flatMapToInt(Arrays::stream)
                .filter(x -> x == 0)
                .count();
        long countOfPlayer = Arrays.stream(field)
                .flatMapToInt(Arrays::stream)
                .filter(x -> x == PLAYER)
                .count();

        if (countOfZeros == 8 && countOfPlayer == 1) {
            return true;
        }
        return false;
    }

    // Проверяем последующие ходы игрока
    public static boolean checkMove(int[][] prevField, int[][] currField) {
        int diffCount = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (prevField[i][j] == 0 && (currField[i][j] == X || currField[i][j] == O)) {
                    diffCount++;
                } else if (prevField[i][j] != currField[i][j]) {
                    return false;
                }
            }
        }

        if (diffCount > 1 || diffCount == 0) {
            return false;
        }

        return true;
    }
}
