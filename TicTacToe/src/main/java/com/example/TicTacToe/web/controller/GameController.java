package com.example.TicTacToe.web.controller;

import ch.qos.logback.core.model.Model;
import com.example.TicTacToe.datasource.model.FreeGame;
import com.example.TicTacToe.domain.model.User;
import com.example.TicTacToe.domain.service.UserService;
import com.example.TicTacToe.domain.service.GameService;
import static com.example.TicTacToe.web.mapper.WebDomainMapper.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.TicTacToe.web.dto.GameRequest;
import com.example.TicTacToe.web.dto.GameResponse;
import com.example.TicTacToe.web.dto.NewGameParam;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public GameController(GameService gameService, UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    @PostMapping("/new")
    public ResponseEntity<GameResponse> createNewGame(@RequestBody NewGameParam gameParam, Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return new ResponseEntity<>(toResponse(gameService.getNewGame(gameParam, userId)), CREATED);
    }

    @GetMapping("{id}/join")
    public ResponseEntity<GameResponse> joinGame(@PathVariable UUID id, Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        GameResponse response = toResponse(gameService.joinGame(id, userId));
        messagingTemplate.convertAndSend("/topic/game/" + id, response);
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GameResponse> getGame(@PathVariable UUID id, Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        GameResponse response = toResponse(gameService.getGame(id, userId));
        return new ResponseEntity<>(response, OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<GameResponse> postMove(
            @PathVariable UUID id,
            @RequestBody GameRequest gameRequest,
            Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        GameResponse response = toResponse(gameService.postMove(gameRequest, id, userId));
        messagingTemplate.convertAndSend("/topic/game/" + id, response);
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping("/waiting-for-prayers")
    public ResponseEntity<List<FreeGame>> getFreeGame(Principal principal) {
        // TODO Выводить список только игр тез игр где нет данного пользователя
        return new ResponseEntity<>(gameService.getFreeGames(), OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserIngo(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.getUserById(id), OK);
    }
}
