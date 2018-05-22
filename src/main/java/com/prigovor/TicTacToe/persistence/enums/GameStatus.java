package com.prigovor.TicTacToe.persistence.enums;

/**
 * This enum represents three type of statuses.
 * X_WON - it means that crosses has won;
 * O_WON - it means that nulls has won;
 * DRAW - it then draw;
 * IN_PROGRESS - it means that the game is in the process;
 *
 * @author Prigovor
 */
public enum GameStatus {
    NEXT_X,
    NEXT_O,
    FILLED,
    X_WON,
    O_WON,
    DRAW,
    IN_PROGRESS;

    public boolean isXWon() {
        return this == X_WON;
    }

    public boolean isOWon() {
        return this == O_WON;
    }

    public boolean isDraw() {
        return this == DRAW;
    }

    public boolean isInProgress() {
        return this == IN_PROGRESS;
    }
}