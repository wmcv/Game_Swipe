package com.mcvteam.gs_zone.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcvteam.gs_zone.elo_score.Elo_ScoreService;

@RestController
@RequestMapping(path = "api/v1/game")
public class GameController {
    private final GameService gameService;
    private Elo_ScoreService elo_ScoreService;
    @Autowired
    public GameController(GameService gameService)
    {
        this.gameService = gameService;
    }


    @GetMapping
    public List<Game> getGames 
    (
        @RequestParam(required = false) Integer id,
        @RequestParam(required = false) String name
    )
    {
        if (id == null && name == null)
        {
            return gameService.getGames();
        }
        else if (id != null && name == null)
        {
            return gameService.getGameById(id);
        }
        else
        {
            return gameService.getGameByName(name);
        }
    }
    
    //@GetMapping("/random")
    //public List<Game> getRandomGames() {
     //   return gameService.getRandomGames(2);
    //}

    









}
