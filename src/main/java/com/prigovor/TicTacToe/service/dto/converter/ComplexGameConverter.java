package com.prigovor.TicTacToe.service.dto.converter;

import com.prigovor.TicTacToe.persistence.api.MoveDao;
import com.prigovor.TicTacToe.persistence.domain.Game;
import com.prigovor.TicTacToe.service.dto.ComplexGameDto;
import com.prigovor.TicTacToe.service.dto.MoveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ComplexGameConverter implements Converter<Game, ComplexGameDto> {

    private MoveDao moveDao;
    private MoveConverter moveConverter;

    @Override
    public ComplexGameDto toDto(Game source) {
        final ComplexGameDto target = new ComplexGameDto();
        target.setGameId(source.getGameId());
        target.setGameTitle(source.getTitle());
        target.setGameStatus(source.getGameStatus().name());
        final List<MoveDto> moveDto = moveConverter
                .toDto(moveDao.loadByGameId(source.getGameId()));
        if (!moveDto.isEmpty()) {
            target.setMoveDtoList(moveDto);
        } else {
            target.setMoveDtoList(Collections.emptyList());
        }
        return target;
    }

    @Override
    public List<ComplexGameDto> toDto(List<Game> gameList) {
        return gameList.parallelStream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /*Dependency injections*/
    @Autowired
    public void setMoveDao(MoveDao moveDao) {
        this.moveDao = moveDao;
    }

    @Autowired
    public void setMoveConverter(MoveConverter moveConverter) {
        this.moveConverter = moveConverter;
    }
}