package com.example.TicTacToe.domain.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    private long countWin;
    private long countDraw;
    private long countLose;
}