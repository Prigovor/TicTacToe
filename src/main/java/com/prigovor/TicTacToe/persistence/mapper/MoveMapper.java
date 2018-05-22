package com.prigovor.TicTacToe.persistence.mapper;

import com.prigovor.TicTacToe.persistence.domain.Move;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MoveMapper implements RowMapper<Move> {

    @Override
    public Move mapRow(ResultSet rs, int rowNum) throws SQLException {
        Move result = new Move();
        result.setMoveId(rs.getLong("move_id"));
        result.setGameId(rs.getLong("game_id"));
        result.setNumber(rs.getInt("number"));
        result.setX(rs.getInt("x"));
        result.setY(rs.getInt("y"));
        return result;
    }
}