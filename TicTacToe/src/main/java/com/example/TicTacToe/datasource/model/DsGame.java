package com.example.TicTacToe.datasource.model;

import com.example.TicTacToe.web.dto.GameState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "game")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class DsGame {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    @Column(nullable = false, length = 9)
    private String field;
    @Column(name = "first_player")
    private UUID firstPlayer;
    @Column(name = "second_player")
    private UUID secondPlayer;
    @Column(name = "play_with_ai")
    private Boolean playWithAi;
    @Enumerated(EnumType.STRING)
    @Column(name = "game_state")
    private GameState gameState;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDate createdAt;
}
