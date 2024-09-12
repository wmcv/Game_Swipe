package com.mcvteam.gs_zone.elo_score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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



    public List<Elo_Score> getTwoRandomEloScoresWithSimilarElo() {
        List<Elo_Score> allScores = StreamSupport
                .stream(elo_ScoreRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Collections.shuffle(allScores);
        for (int i = 0; i < allScores.size() - 1; i++) {
            Elo_Score score1 = allScores.get(i);
            Elo_Score score2 = allScores.get(i + 1);
            if (Math.abs(score1.getElo_Score() - score2.getElo_Score()) < 50) { // Adjust similarity threshold as needed
                return List.of(score1, score2);
            }
        }
        return List.of(); // or handle case where no similar scores are found
    }





}
