package com.example.TicTacToe.di;

import com.example.TicTacToe.datasource.repository.GameRepository;
import com.example.TicTacToe.domain.service.GameService;
import com.example.TicTacToe.domain.service.UserService;
import org.springframework.context.annotation.Bean;

public class DependencyConfig {
    @Bean
    public GameService gameService(GameRepository gameRepository, UserService userService) {
        return new GameService(gameRepository, userService);
    }
}
