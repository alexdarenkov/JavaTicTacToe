package com.example.TicTacToe.datasource.model;

import com.example.TicTacToe.domain.model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DsUser {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "count_win")
    private long countWin = 0;
    @Column(name = "count_draw")
    private long countDraw = 0;
    @Column(name = "count_lose")
    private long countLose = 0;
}