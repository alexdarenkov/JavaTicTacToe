package com.example.TicTacToe.datasource.repository;
import com.example.TicTacToe.datasource.model.DsUser;
import com.example.TicTacToe.datasource.model.FreeGame;
import com.example.TicTacToe.domain.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<DsUser, UUID> {
    Optional<DsUser> findByLogin(String login);

    @Query("""
        SELECT u
        FROM DsUser u
        ORDER BY u.countWin DESC
        """)
    List<DsUser> topUsers(Pageable pageable);
}
