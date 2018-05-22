package com.prigovor.TicTacToe.persistence.impl;

import com.prigovor.TicTacToe.persistence.api.MoveDao;
import com.prigovor.TicTacToe.persistence.domain.Move;
import com.prigovor.TicTacToe.persistence.mapper.MoveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MoveJDBCDao implements MoveDao {

    private MoveMapper moveMapper;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Move> loadByGameId(Long gameId) {
        final String query = "SELECT * FROM move WHERE game_id = :gameId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("gameId", String.valueOf(gameId));
        return jdbcTemplate.query(query, parameters, moveMapper);
    }

    @Override
    public Long save(Move move) {
        final String query = "INSERT INTO move (game_id, number, x, y) " +
                "VALUES (:gameId, :number, :x, :y)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("gameId", move.getGameId());
        parameters.addValue("number", move.getNumber());
        parameters.addValue("x", move.getX());
        parameters.addValue("y", move.getY());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, parameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Move move) {
        final String query = "UPDATE move SET " +
                "game_id = :gameId, " +
                "number = :number, " +
                "x = :x, y = :y " +
                "WHERE move_id = :moveId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("moveId", move.getMoveId());
        parameters.addValue("gameId", move.getGameId());
        parameters.addValue("number", move.getNumber());
        parameters.addValue("x", move.getX());
        parameters.addValue("y", move.getY());
        jdbcTemplate.update(query, parameters);
    }

    @Override
    public void deleteBayGameId(Long gameId) {
        final String query = "DELETE FROM move WHERE game_id = :gameId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("gameId", gameId);
        jdbcTemplate.update(query, parameters);
    }

    /*Dependency injections*/
    @Autowired
    public void setMoveMapper(MoveMapper moveMapper) {
        this.moveMapper = moveMapper;
    }

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}