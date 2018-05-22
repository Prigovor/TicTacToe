package com.prigovor.TicTacToe.service.dto.converter;


import com.prigovor.TicTacToe.persistence.domain.Game;
import com.prigovor.TicTacToe.persistence.enums.GameStatus;
import com.prigovor.TicTacToe.service.dto.GameDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameConverter implements Converter<Game, GameDto> {
    @Override
    public Game toDomain(GameDto source) {
        final Game target = new Game();
        target.setGameId(source.getGameId());
        target.setTitle(source.getGameTitle());
        target.setGameStatus(GameStatus.valueOf(source.getGameStatus()));
        return target;
    }

    @Override
    public GameDto toDto(Game source) {
        final GameDto target = new GameDto();
        target.setGameId(source.getGameId());
        target.setGameTitle(source.getTitle());
        target.setGameStatus(source.getGameStatus().name());
        return target;
    }

    @Override
    public List<Game> toDomain(List<GameDto> gameDtoList) {
        return gameDtoList.parallelStream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDto> toDto(List<Game> gameList) {
        return gameList.parallelStream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}