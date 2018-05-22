package com.prigovor.TicTacToe.service.api;

import com.prigovor.TicTacToe.service.dto.GameDto;

import java.util.List;

public interface GameService {

    Long saveOrUpdate(final GameDto gameDto);

    GameDto findById(final Long gameId);

    void delete(final Long gameId);

    List<GameDto> findAll();

    public String getGameDtoAsJson(Long gameId);
}