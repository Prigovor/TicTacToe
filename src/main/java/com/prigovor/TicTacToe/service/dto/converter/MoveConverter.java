package com.prigovor.TicTacToe.service.dto.converter;

import com.prigovor.TicTacToe.persistence.domain.Move;
import com.prigovor.TicTacToe.service.api.Constants;
import com.prigovor.TicTacToe.service.dto.MoveDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoveConverter implements Converter<Move, MoveDto> {

    @Override
    public Move toDomain(MoveDto source) {
        final Move target = new Move();
        target.setMoveId(source.getMoveId());
        target.setGameId(source.getGameId());
        target.setNumber(source.getNumber());
        target.setX(source.getX());
        target.setY(source.getY());
        return target;
    }

    @Override
    public MoveDto toDto(Move source) {
        final MoveDto target = new MoveDto();
        target.setMoveId(source.getMoveId());
        target.setGameId(source.getGameId());
        target.setNumber(source.getNumber());
        target.setX(source.getX());
        target.setY(source.getY());
        if (source.getNumber() != null) {
            if (source.getNumber() % 2 == 0) {
                target.setSign(Constants.O_SIGN.toString());
            } else {
                target.setSign(Constants.X_SIGN.toString());
            }
        }
        return target;
    }

    @Override
    public List<Move> toDomain(List<MoveDto> moveDtoList) {
        return moveDtoList.parallelStream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MoveDto> toDto(List<Move> moveList) {
        return moveList.parallelStream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}