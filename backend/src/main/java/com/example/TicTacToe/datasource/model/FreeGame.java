package com.example.TicTacToe.datasource.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FreeGame {
    UUID id;
    String login;
}
