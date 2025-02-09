package com.example.TicTacToe.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
public class User {
    private UUID id;
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}