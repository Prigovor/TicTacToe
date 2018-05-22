package com.prigovor.TicTacToe.controller;

import com.prigovor.TicTacToe.service.api.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

    private GameService gameService;
    private final Logger logger = LoggerFactory.getLogger(IndexController.class.getName());

    @GetMapping
    public ModelAndView indexPage(ModelAndView mav) {
        mav.addObject("dtoList", gameService.findAll());
        mav.setViewName("index");
        return mav;
    }

    /*Dependency injections*/

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}