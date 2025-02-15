package com.example.TicTacToe.datasource.repository;

import com.example.TicTacToe.datasource.model.DsGame;
import com.example.TicTacToe.datasource.model.FreeGame;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<DsGame, UUID> {
    @Query("""
        SELECT NEW com.example.TicTacToe.datasource.model.FreeGame(g.id, u.login) 
        FROM DsGame g 
        JOIN DsUser u ON g.firstPlayer = u.id OR g.secondPlayer = u.id 
        WHERE (g.firstPlayer IS NULL OR g.secondPlayer IS NULL) 
        AND g.playWithAi = false
        ORDER BY g.createdAt
        """)
    List<FreeGame> findFreeGames(Pageable pageable);

    @Query(value = """
        SELECT * FROM game
        WHERE (first_player = :userId OR second_player = :userId)
        AND game_state IN ('DRAW', 'FIRST_PLAYER_WON', 'SECOND_PLAYER_WON')
        """, nativeQuery = true)
    List<DsGame> findFinishedGames(@Param("userId") UUID userId);
}
