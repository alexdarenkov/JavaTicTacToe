package com.example.TicTacToe.domain.service;

import com.example.TicTacToe.datasource.repository.GameRepository;
import com.example.TicTacToe.domain.model.DomainGame;
import com.example.TicTacToe.web.model.WebGame;
import org.springframework.stereotype.Service;

import static com.example.TicTacToe.datasource.mapper.DataSourceDomainMapper.toDataSourceGame;
import static com.example.TicTacToe.web.mapper.WebDomainMapper.toDomainGame;
import static com.example.TicTacToe.domain.service.GameLogic.*;

@Service
public class GameService implements ServiceInterface {
    private static final int PLAYER = 1;
    private static final int COMPUTER = 2;

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public String isValidMove(DomainGame domainGame) {
        int[][] currField = domainGame.getGameField().getField();

        if (!gameRepository.containsGame(toDataSourceGame(domainGame))) {
            if (!checkFirstMove(currField)) {
                System.out.println("Invalid first move");
                return "Invalid move";
            }
            gameRepository.saveGame(toDataSourceGame(domainGame));
            return "OK";
        }

        int[][] prevField = gameRepository.getGame(domainGame.getId()).getGameField().getField();

        if (!checkMove(prevField, currField)) {
            return "Invalid move";
        }

        return "OK";
    }

    @Override
    public String getGameStatus(DomainGame domainGame) {
        int[][] field = domainGame.getGameField().getField();
        if (isWin(field, PLAYER)) return "WIN";
        if (isWin(field, COMPUTER)) return "LOSE";
        if (isDraw(field)) return "DRAW";
        return null;
    }

    @Override
    public DomainGame getNextMove(DomainGame domainGame) {
        int[][] field = domainGame.getGameField().getField();
        int[] bestMove = findBestMove(field);
        domainGame.setGameFieldCeil(bestMove[0], bestMove[1], COMPUTER);
        gameRepository.saveGame(toDataSourceGame(domainGame));
        return domainGame;
    }

    public void addNewGame(WebGame webGame) {
        gameRepository.saveGame(toDataSourceGame(toDomainGame(webGame)));
    }
}
