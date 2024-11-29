package com.example.TicTacToe.web.controller;

import com.example.TicTacToe.web.model.WebGame;
import com.example.TicTacToe.domain.service.GameService;
import static com.example.TicTacToe.web.mapper.WebDomainMapper.toDomainGame;
import static com.example.TicTacToe.web.mapper.WebDomainMapper.toWebGame;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Создаю новую игру
    @GetMapping("/new")
    public ResponseEntity<WebGame> createNewGame() {
        WebGame webGame = new WebGame(UUID.randomUUID());
        gameService.addNewGame(webGame);
        return ResponseEntity.ok(webGame);
    }

    @PostMapping("{id}")
    public ResponseEntity<WebGame> updateGame(@PathVariable UUID id, @RequestBody WebGame webGame) {
        String errorMessage = gameService.isValidMove(toDomainGame(webGame));

        if (errorMessage.equals("OK")) {
            WebGame responseWebGame = toWebGame(gameService.getNextMove(toDomainGame(webGame)));
            String gameStatus = gameService.getGameStatus(toDomainGame(responseWebGame));
            responseWebGame.setErrorMessage(errorMessage);
            responseWebGame.setGameStatus(gameStatus);
            return ResponseEntity.ok(responseWebGame);
        }

        webGame.setErrorMessage(errorMessage);
        return ResponseEntity.badRequest().body(webGame);
    }
}
