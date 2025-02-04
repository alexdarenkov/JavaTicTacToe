package com.example.TicTacToe.di;

import com.example.TicTacToe.datasource.repository.GameRepository;
import com.example.TicTacToe.domain.service.GameService;
import org.springframework.context.annotation.Bean;

public class DependencyConfig {
    @Bean
    public GameService gameService(GameRepository gameRepository) {
        return new GameService(gameRepository);
    }
}
