package com.prigovor.TicTacToe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prigovor.TicTacToe.persistence.api.MoveDao;
import com.prigovor.TicTacToe.persistence.enums.GameStatus;
import com.prigovor.TicTacToe.service.api.GameProcessor;
import com.prigovor.TicTacToe.service.api.MoveService;
import com.prigovor.TicTacToe.service.dto.GameDto;
import com.prigovor.TicTacToe.service.dto.MoveDto;
import com.prigovor.TicTacToe.service.dto.converter.MoveConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoveServiceImpl implements MoveService {

    private MoveDao moveDao;
    private MoveConverter moveConverter;
    private GameProcessor gameProcessor;
    private final Logger logger = LoggerFactory.getLogger(MoveServiceImpl.class.getName());

    @Override
    @Transactional(readOnly = true)
    public List<MoveDto> findByGameId(Long gameId) {
        return moveConverter.toDto(moveDao.loadByGameId(gameId));
    }

    @Override
    @Transactional
    public Long saveOrUpdate(MoveDto moveDto) {
        if (moveDto.getMoveId() == null) {
            return moveDao.save(moveConverter.toDomain(moveDto));
        } else {
            moveDao.update(moveConverter.toDomain(moveDto));
            return moveDto.getMoveId();
        }
    }

    @Override
    @Transactional
    public void deleteBayGameId(Long gameId) {
        moveDao.deleteBayGameId(gameId);
    }

    @Override
    @Transactional
    public GameStatus makeTurnProcess(GameDto gameDto) {
        return gameProcessor.makeMove(gameDto);
    }

    @Override
    @Transactional(readOnly = true)
    public String getMoveDtoAsJson(Long gameId) {
        String json = "";
        final ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(findByGameId(gameId));
        } catch (JsonProcessingException e) {
            logger.error("Get complex game dto as json error: " + e.getMessage());
        }
        return json;
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

    @Autowired
    public void setGameProcessor(GameProcessor gameProcessor) {
        this.gameProcessor = gameProcessor;
    }
}
