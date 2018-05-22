package com.prigovor.TicTacToe.controller;

import com.prigovor.TicTacToe.service.api.GameService;
import com.prigovor.TicTacToe.service.api.MoveService;
import com.prigovor.TicTacToe.service.dto.GameDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/game")
public class GameController {

    private GameService gameService;
    private MoveService moveService;
    private final Logger logger = LoggerFactory.getLogger(GameController.class.getName());

    @GetMapping
    public String gamePage() {
        return "game_page";
    }

    @PostMapping("/newGame")
    public RedirectView newGame(@RequestParam final String gameTitle,
                                final RedirectAttributes attributes) {
        final GameDto gameDto = new GameDto();
        gameDto.setGameTitle(gameTitle);
        gameDto.setGameStatus("IN_PROGRESS");
        final Long gameId = gameService.saveOrUpdate(gameDto);
        attributes.addFlashAttribute("flashAttribute", "newGame");
        attributes.addAttribute("attribute", "newGame");
        return new RedirectView("/game/view/" + gameId);
    }

    @GetMapping("/saveGame")
    public RedirectView saveGame(final RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute", "saveGame");
        attributes.addAttribute("attribute", "saveGame");
        return new RedirectView("/");
    }

    @GetMapping("/deleteGame/{gameId}")
    public RedirectView deleteGame(@PathVariable final Long gameId,
                                   final RedirectAttributes attributes) {
        moveService.deleteBayGameId(gameId);
        gameService.delete(gameId);
        attributes.addFlashAttribute("flashAttribute", "/deleteGame/{gameId}");
        attributes.addAttribute("attribute", "/deleteGame/{gameId}");
        return new RedirectView("/");
    }

    @GetMapping("/view/{gameId}")
    private ModelAndView viewGame(@PathVariable Long gameId,
                                  ModelAndView modelAndView) {
        modelAndView.addObject("gameDto", gameService.getGameDtoAsJson(gameId));
        modelAndView.addObject("moveList", moveService.getMoveDtoAsJson(gameId));
        modelAndView.setViewName("game_page");
        return modelAndView;
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