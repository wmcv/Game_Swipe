package com.mcvteam.gs_zone.elo_score;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcvteam.gs_zone.game.Game;
import com.mcvteam.gs_zone.game.GameService;

@RestController
@RequestMapping(path = "api/v1/eloscore")
public class Elo_ScoreController {
    private final Elo_ScoreService elo_ScoreService;
    private final GameService gameService;

    @Autowired
    public Elo_ScoreController(Elo_ScoreService elo_ScoreService, GameService gameService)
    {
        this.elo_ScoreService = elo_ScoreService;
        this.gameService = gameService;
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


    @GetMapping(path="/random-pair")
    public Map<String, Object> getRandomEloScorePair() {
        // Retrieve two random Elo_Score objects with similar Elo scores
        List<Elo_Score> randomScores = elo_ScoreService.getTwoRandomEloScoresWithSimilarElo();
        
        if (randomScores.size() != 2) {
            return Map.of("error", "Not enough scores found.");
        }

        // Collect game IDs
        Set<Integer> gameIds = randomScores.stream()
                .map(Elo_Score::getGame_Id)
                .collect(Collectors.toSet());

        // Retrieve game details by game IDs
        Map<Integer, Game> games = gameService.getGamesById(gameIds);

        // Retrieve game information
        Elo_Score score1 = randomScores.get(0);
        Elo_Score score2 = randomScores.get(1);
        Game game1 = games.get(score1.getGame_Id());
        Game game2 = games.get(score2.getGame_Id());

        if (game1 == null || game2 == null) {
            return Map.of("error", "Game details not found.");
        }

        // Prepare and return response
        return Map.of(
                "game1", Map.of(
                        "name", game1.getName(),
                        "imageUrl", game1.getImage_Url()
                ),
                "game2", Map.of(
                        "name", game2.getName(),
                        "imageUrl", game2.getImage_Url()
                )
        );
    }
    


}

