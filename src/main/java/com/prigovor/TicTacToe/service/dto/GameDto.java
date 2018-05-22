package com.prigovor.TicTacToe.service.dto;

public class GameDto {

    private Long gameId;
    private String gameTitle;
    private String gameStatus;
    private MoveDto currentMoveDto;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public MoveDto getCurrentMoveDto() {
        return currentMoveDto;
    }

    public void setCurrentMoveDto(MoveDto currentMoveDto) {
        this.currentMoveDto = currentMoveDto;
    }
}