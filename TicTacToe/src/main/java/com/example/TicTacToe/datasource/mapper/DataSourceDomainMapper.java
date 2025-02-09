package com.example.TicTacToe.datasource.mapper;


import com.example.TicTacToe.datasource.model.DsGame;
import com.example.TicTacToe.datasource.model.DsUser;
import com.example.TicTacToe.domain.model.Game;
import com.example.TicTacToe.domain.model.User;

import java.util.Collections;

import static com.example.TicTacToe.domain.service.GameService.SIZE;

public class DataSourceDomainMapper {
    public static int[][] stringToMatrix(String string) {
        final int n = SIZE;
        int[][] fieldMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fieldMatrix[i][j] = Character.getNumericValue(string.charAt(i * n + j));
            }
        }

        return fieldMatrix;
    }

    public static String matrixToString(int[][] matrix) {
        final int n = SIZE;
        StringBuilder fieldString = new StringBuilder();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fieldString.append(Character.forDigit(matrix[i][j], 10));
            }
        }

        return fieldString.toString();
    }

    public static Game dsToDomainGame(DsGame dsGame) {
        String fieldString = dsGame.getField();
        Game game = new Game();
        game.setId(dsGame.getId());
        game.setField(stringToMatrix(fieldString));
        game.setFirstPlayer(dsGame.getFirstPlayer());
        game.setSecondPlayer(dsGame.getSecondPlayer());
        game.setPlayWithAi(dsGame.getPlayWithAi());
        game.setGameState(dsGame.getGameState());
        return game;
    }

    public static DsGame toDsGame(Game domainGame) {
        int[][] fieldMatrix = domainGame.getField();
        DsGame dsGame = new DsGame();
        dsGame.setId(domainGame.getId());
        dsGame.setField(matrixToString(fieldMatrix));
        dsGame.setFirstPlayer(domainGame.getFirstPlayer());
        dsGame.setSecondPlayer(domainGame.getSecondPlayer());
        dsGame.setPlayWithAi(domainGame.isPlayWithAi());
        dsGame.setGameState(domainGame.getGameState());
        return dsGame;
    }

    public static User toDomainUser(DsUser dataSourceUser) {
        User user = new User();
        user.setId(dataSourceUser.getId());
        user.setLogin(dataSourceUser.getLogin());
        user.setPassword(dataSourceUser.getPassword());
        user.setRoles(Collections.singleton(dataSourceUser.getRole()));
        System.out.println(user.getRoles());
        return user;
    }

    public static DsUser toDataSourceUser(User user) {
        DsUser dataSourceUser = new DsUser();
        dataSourceUser.setId(user.getId());
        dataSourceUser.setLogin(user.getLogin());
        dataSourceUser.setPassword(user.getPassword());
        dataSourceUser.setRole(user.getRoles().iterator().next());
        return dataSourceUser;
    }
}
