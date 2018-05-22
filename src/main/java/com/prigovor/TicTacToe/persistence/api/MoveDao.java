package com.prigovor.TicTacToe.persistence.api;

import com.prigovor.TicTacToe.persistence.domain.Move;

import java.util.List;

public interface MoveDao {

    List<Move> loadByGameId(final Long gameId);

    Long save(final Move move);

    void update(final Move move);

    void deleteBayGameId(final Long gameId);

}