CREATE TABLE game (
    id UUID PRIMARY KEY,
    field VARCHAR(9) NOT NULL CHECK (LENGTH(field) = 9),
    first_player UUID,
    second_player UUID,
    play_with_ai boolean,
    game_state VARCHAR(255),
    created_at DATE
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

TRUNCATE TABLE game;
TRUNCATE TABLE users;