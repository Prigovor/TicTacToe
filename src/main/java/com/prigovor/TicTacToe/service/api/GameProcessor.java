package com.prigovor.TicTacToe.service.api;

import com.prigovor.TicTacToe.persistence.enums.GameStatus;
import com.prigovor.TicTacToe.service.dto.GameDto;

public interface GameProcessor {

    GameStatus makeMove(GameDto gameDto);

}