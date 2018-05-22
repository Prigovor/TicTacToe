package com.prigovor.TicTacToe.persistence.api;

import com.prigovor.TicTacToe.persistence.domain.Game;

import java.util.List;

public interface GameDao {

    Long save(final Game game);

    Game loadById(final Long gameId);

    void update(final Game game);

    void delete(final Long gameId);

    List<Game> loadAll();

}