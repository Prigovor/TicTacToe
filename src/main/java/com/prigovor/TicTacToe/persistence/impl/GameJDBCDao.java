package com.prigovor.TicTacToe.persistence.impl;

import com.prigovor.TicTacToe.persistence.api.GameDao;
import com.prigovor.TicTacToe.persistence.domain.Game;
import com.prigovor.TicTacToe.persistence.mapper.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameJDBCDao implements GameDao {

    private GameMapper gameMapper;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Long save(Game game) {
        final String query = "INSERT INTO game (title, status) VALUES (:title, :status)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", game.getTitle());
        parameters.addValue("status", game.getGameStatus().name());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, parameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Game loadById(Long gameId) {
        final String query = "SELECT game_id, title, status FROM game WHERE game_id = :gameId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("gameId", String.valueOf(gameId));
        return jdbcTemplate.queryForObject(query, parameters, gameMapper);
    }

    @Override
    public void update(Game game) {
        final String query = "UPDATE game SET title = :title, status = :status WHERE game_id = :gameId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("gameId", game.getGameId());
        parameters.addValue("title", game.getTitle());
        parameters.addValue("status", game.getGameStatus().name());
        jdbcTemplate.update(query, parameters);
    }

    @Override
    public void delete(Long gameId) {
        final String query = "DELETE FROM game WHERE game_id = :gameId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("gameId", gameId);
        jdbcTemplate.update(query, parameters);
    }

    @Override
    public List<Game> loadAll() {
        final String query = "SELECT * FROM game";
        return jdbcTemplate.query(query, gameMapper);
    }

    /*Dependency injections*/
    @Autowired
    public void setGameMapper(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    @Autowired
    public void setTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}