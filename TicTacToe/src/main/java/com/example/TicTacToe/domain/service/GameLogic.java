package com.example.TicTacToe.domain.service;

import static com.example.TicTacToe.domain.service.GameService.SIZE;

public final class GameLogic {
    private static final int WEIGHT = 10;

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


    static int minimax(int[][] field, int currPlayer, int player, int computer)
    {
        if (isWin(field, computer)) return WEIGHT;
        if (isWin(field, player)) return -WEIGHT;
        if (isDraw(field)) return 0;

        // Ход компьютера
        if (currPlayer == computer) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (field[i][j] == 0) {
                        field[i][j] = computer;
                        best = Math.max(best, minimax(field, player, player, computer));
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
                        field[i][j] = player;
                        best = Math.min(best, minimax(field, computer, player, computer));
                        field[i][j] = 0;
                    }
                }
            }

            return best;
        }
    }

    // Находим самый выгодный ход для компьютера
    static int[] findBestMove(int[][] field, int player, int computer)
    {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == 0) {
                    field[i][j] = computer;
                    int currScore = minimax(field, player, player, computer);
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
}
