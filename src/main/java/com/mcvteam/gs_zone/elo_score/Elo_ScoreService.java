package com.mcvteam.gs_zone.elo_score;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Elo_ScoreService {
    private final Elo_ScoreRepository elo_ScoreRepository;

    @Autowired
    public Elo_ScoreService(Elo_ScoreRepository elo_ScoreRepository)
    {
        this.elo_ScoreRepository = elo_ScoreRepository;
    }


    public List<Elo_Score> getElo_Scores()
    {
        return elo_ScoreRepository.findAll();
    }


    public List<Elo_Score> getElo_ScoresFromGame(Integer id)
    {
        return elo_ScoreRepository.findAll().stream()
                        .filter(elo_Score -> id.equals(elo_Score.getGame_Id()))
                        .collect(Collectors.toList());
    }


    public List<Elo_Score> getLatestElo_Scores()
    {
        List<Elo_Score> allScores = elo_ScoreRepository.findAll();
        Map<Integer, Elo_Score> latestScoresByGame = allScores.stream()
                .collect(Collectors.toMap(
                        Elo_Score::getGame_Id,            
                        score -> score,                 
                        (existing, replacement) ->      
                        existing.getTimeStamp().toInstant().isAfter(replacement.getTimeStamp().toInstant()) ? existing : replacement
                        ));
            return new ArrayList<>(latestScoresByGame.values());
    }




    public Elo_Score addElo_Score(Elo_Score elo_Score)
    {
        elo_ScoreRepository.save(elo_Score);
        return elo_Score;
    }







}
