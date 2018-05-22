package com.prigovor.TicTacToe.service.api;

import com.prigovor.TicTacToe.persistence.enums.GameStatus;
import com.prigovor.TicTacToe.service.dto.GameDto;
import com.prigovor.TicTacToe.service.dto.MoveDto;

import java.util.List;

public interface MoveService {

    List<MoveDto> findByGameId(final Long gameId);

    Long saveOrUpdate(final MoveDto moveDto);

    void deleteBayGameId(final Long gameId);

    public GameStatus makeTurnProcess(final GameDto moveDto);

    String getMoveDtoAsJson(Long gameId);
}