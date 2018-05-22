package com.prigovor.TicTacToe.persistence.mapper;

import com.prigovor.TicTacToe.persistence.domain.Game;
import com.prigovor.TicTacToe.persistence.enums.GameStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GameMapper implements RowMapper<Game> {
    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Game result = new Game();
        result.setGameId(rs.getLong("game_id"));
        result.setTitle(rs.getString("title"));
        result.setGameStatus(GameStatus.valueOf(rs.getString("status")));
        return result;
    }
}
