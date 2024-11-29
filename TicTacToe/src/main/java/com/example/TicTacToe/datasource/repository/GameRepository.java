package com.example.TicTacToe.datasource.repository;

import com.example.TicTacToe.datasource.model.DataSourceGame;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class GameRepository {
    private final ConcurrentMap<UUID, DataSourceGame> gameStore = new ConcurrentHashMap<>();

    public void saveGame(DataSourceGame game) {
        gameStore.put(game.getId(), game);
    }

    public DataSourceGame getGame(UUID id) {
        return gameStore.get(id);
    }

    public boolean containsGame(DataSourceGame game) {
        return gameStore.containsKey(game.getId());
    }
}
