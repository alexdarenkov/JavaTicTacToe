package com.example.TicTacToe.datasource.repository;
import com.example.TicTacToe.datasource.model.DsUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<DsUser, UUID> {
    Optional<DsUser> findByLogin(String login);
}
