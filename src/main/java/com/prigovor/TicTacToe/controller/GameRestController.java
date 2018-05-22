package com.prigovor.TicTacToe.controller;

import com.prigovor.TicTacToe.persistence.enums.GameStatus;
import com.prigovor.TicTacToe.service.api.GameService;
import com.prigovor.TicTacToe.service.api.MoveService;
import com.prigovor.TicTacToe.service.dto.GameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restGame")
public class GameRestController {

    private GameService gameService;
    private MoveService moveService;

    @PostMapping("/makeMove")
    public ResponseEntity<GameStatus> makeMove(@RequestBody GameDto moveDto) {
        GameStatus gameStatus = moveService.makeTurnProcess(moveDto);
        GameDto gameDto = gameService.findById(moveDto.getGameId());
        gameDto.setGameStatus(gameStatus.toString());
        gameService.saveOrUpdate(gameDto);
        return new ResponseEntity<>(gameStatus, HttpStatus.OK);
    }

    /*Dependency injections*/
    @Autowired
    public void setService(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    public void setMoveService(MoveService moveService) {
        this.moveService = moveService;
    }
}
