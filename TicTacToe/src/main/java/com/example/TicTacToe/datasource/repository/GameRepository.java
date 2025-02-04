package com.example.TicTacToe.datasource.repository;

import com.example.TicTacToe.datasource.model.DsGame;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<DsGame, UUID> {
    @Query("SELECT id FROM DsGame WHERE (firstPlayer IS NULL OR secondPlayer IS NULL) AND playWithAi = false")
    List<UUID> findFreeGames(Pageable pageable);
}
