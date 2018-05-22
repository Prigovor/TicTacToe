package com.prigovor.TicTacToe.persistence.domain;

import com.prigovor.TicTacToe.persistence.enums.GameStatus;

import java.io.Serializable;


public class Game implements Serializable {

    private Long gameId;
    private String title;
    private GameStatus gameStatus;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}