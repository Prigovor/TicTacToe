package com.prigovor.TicTacToe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prigovor.TicTacToe.persistence.api.GameDao;
import com.prigovor.TicTacToe.service.api.GameService;
import com.prigovor.TicTacToe.service.dto.GameDto;
import com.prigovor.TicTacToe.service.dto.converter.GameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private GameDao gameDao;
    private GameConverter gameConverter;
    private final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class.getName());

    @Override
    @Transactional
    public Long saveOrUpdate(GameDto gameDto) {
        if (gameDto.getGameId() == null) {
            return gameDao.save(gameConverter.toDomain(gameDto));
        } else {
            gameDao.update(gameConverter.toDomain(gameDto));
            return gameDto.getGameId();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GameDto findById(Long gameId) {
        return gameConverter.toDto(gameDao.loadById(gameId));
    }

    @Override
    @Transactional
    public void delete(Long gameId) {
        gameDao.delete(gameId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameDto> findAll() {
        return gameConverter.toDto(gameDao.loadAll());
    }

    @Override
    @Transactional(readOnly = true)
    public String getGameDtoAsJson(Long gameId) {
        String json = "";
        final ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(findById(gameId));
        } catch (JsonProcessingException e) {
            logger.error("Get complex game dto as json error: " + e.getMessage());
        }
        return json;
    }

    /*Dependency injections*/
    @Autowired
    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Autowired
    public void setGameConverter(GameConverter gameConverter) {
        this.gameConverter = gameConverter;
    }

}
