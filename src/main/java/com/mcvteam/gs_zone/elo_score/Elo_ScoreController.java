package com.mcvteam.gs_zone.elo_score;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/elo_score")
public class Elo_ScoreController {
    private final Elo_ScoreService elo_ScoreService;

    @Autowired
    public Elo_ScoreController(Elo_ScoreService elo_ScoreService)
    {
        this.elo_ScoreService = elo_ScoreService;
    }


    @GetMapping
    public List<Elo_Score> getElo_Scores 
    (
        @RequestParam(required = false) Integer game_id,
        @RequestParam(required = false) String unique
    )
    {
        if (game_id != null)
        {
            return elo_ScoreService.getElo_ScoresFromGame(game_id);
        }
        else if (unique != null)
        {
            return elo_ScoreService.getLatestElo_Scores();
        }
        else
        {
            return elo_ScoreService.getElo_Scores();
        }
    }
    


    @PostMapping
    public ResponseEntity<Elo_Score> addElo_Score(@RequestBody Elo_Score elo_Score)
    {
        Elo_Score createdElo_Score = elo_ScoreService.addElo_Score(elo_Score);
        return new ResponseEntity<>(createdElo_Score, HttpStatus.CREATED);
    }



    


}

