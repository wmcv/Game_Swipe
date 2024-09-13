package com.mcvteam.gs_zone.elo_score;

import java.sql.Timestamp;
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
    @Autowired
    private final Elo_ScoreRepository elo_ScoreRepository;
    
    //@Autowired
   // private GameRepository gameRepository;


    @Autowired
    public Elo_ScoreService(Elo_ScoreRepository elo_ScoreRepository)
    {
        this.elo_ScoreRepository = elo_ScoreRepository;
        //this.gameRepository = gameRepository;
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
            if (Math.abs(score1.getElo_Score() - score2.getElo_Score()) < 50) { // 50 reps the range of elo two games gotta be in
                return List.of(score1, score2);
            }
        }
        return List.of();
    }




    public void updateEloScores(int winnerId, int loserId) {
        // Fetch the current Elo scores for both games
        Elo_Score winnerElo = elo_ScoreRepository.findLatestByGameId(winnerId);
        Elo_Score loserElo = elo_ScoreRepository.findLatestByGameId(loserId);

        // Calculate new Elo scores (simplified Elo formula)
        int winnerNewElo = calculateNewElo(winnerElo.getElo_Score(), loserElo.getElo_Score(), true);
        int loserNewElo = calculateNewElo(loserElo.getElo_Score(), winnerElo.getElo_Score(), false);

        // Save the new Elo scores
        Elo_Score newWinnerElo = new Elo_Score(winnerId, new Timestamp(System.currentTimeMillis()), winnerNewElo);
        Elo_Score newLoserElo = new Elo_Score(loserId, new Timestamp(System.currentTimeMillis()), loserNewElo);

        elo_ScoreRepository.save(newWinnerElo);
        elo_ScoreRepository.save(newLoserElo);
    }

    private int calculateNewElo(int currentElo, int opponentElo, boolean isWinner) {
        int k = 32; // Elo constant
        double expectedScore = 1.0 / (1.0 + Math.pow(10, (opponentElo - currentElo) / 400.0));
        int score = isWinner ? 1 : 0;
        return (int) (currentElo + k * (score - expectedScore));
    }

    public Elo_Score findLatestByGameId(Integer gameId) {
        List<Elo_Score> scores = elo_ScoreRepository.findByGameIdOrderByTimestampDesc(gameId);
        if (scores.isEmpty()) {
            return null;
        }
        return scores.get(0); // Return the most recent Elo score
    }


}
