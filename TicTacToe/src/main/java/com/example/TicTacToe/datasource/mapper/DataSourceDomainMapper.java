package com.example.TicTacToe.datasource.mapper;


import com.example.TicTacToe.datasource.model.DataSourceGame;
import com.example.TicTacToe.datasource.model.DataSourceGameField;
import com.example.TicTacToe.domain.model.DomainGame;
import com.example.TicTacToe.domain.model.DomainGameField;

public class DataSourceDomainMapper {
    public static DomainGameField toDomainGameField(DataSourceGameField dataSourceGameField) {
        return new DomainGameField(dataSourceGameField.getField());
    }

    public static DataSourceGameField toDataSourceGameField(DomainGameField domainGameField) {
        return new DataSourceGameField(domainGameField.getField());
    }

    public static DomainGame toDomainGame(DataSourceGame dataSourceGame) {
        return new DomainGame(dataSourceGame.getId(), dataSourceGame.getField());
    }

    public static DataSourceGame toDataSourceGame(DomainGame domainGame) {
        return new DataSourceGame(domainGame.getId(), domainGame.getField());
    }
}
