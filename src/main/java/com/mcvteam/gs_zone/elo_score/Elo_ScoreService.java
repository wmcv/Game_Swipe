package com.mcvteam.gs_zone.elo_score;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
@Service
public class Elo_ScoreService {
    @Autowired
    private final Elo_ScoreRepository elo_ScoreRepository;
    @PersistenceContext
    private EntityManager entityManager;
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





    public Elo_Score findLatestByGameId(Integer gameId) {
        List<Elo_Score> optionalScore = elo_ScoreRepository.findLatestByGameId(gameId);
        //if (optionalScore.isPresent()) {
            return optionalScore.get(0);
        //} else {
         //   return null; // Or throw an exception if appropriate
        //}
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
            if (Math.abs(score1.getElo_Score() - score2.getElo_Score()) < 300) { // 50 reps the range of elo two games gotta be in
                return List.of(score1, score2);
            }
        }
        return List.of();
    }



    /*
    public void updateEloScores(int winnerId, int loserId) {
        // Fetch the current Elo scores for both games
        //try {

        Elo_Score winnerElo = elo_ScoreRepository.findLatestByGameId(winnerId);
        Elo_Score loserElo = elo_ScoreRepository.findLatestByGameId(loserId);

        // Calculate new Elo scores (simplified Elo formula)
        int winnerNewElo = calculateNewElo(winnerElo.getElo_Score(), loserElo.getElo_Score(), true);
        int loserNewElo = calculateNewElo(loserElo.getElo_Score(), winnerElo.getElo_Score(), false);

        // Save the new Elo scores
        Elo_Score newWinnerElo = new Elo_Score(winnerId, new Timestamp(System.currentTimeMillis()), winnerNewElo);
        Elo_Score newLoserElo = new Elo_Score(loserId, new Timestamp(System.currentTimeMillis()), loserNewElo);
        //Console.log("we "+newWinnerElo);
        System.out.print("we "+newWinnerElo);
        try {
        elo_ScoreRepository.save(newWinnerElo);
        elo_ScoreRepository.save(newLoserElo);
        }
        catch (Exception e) {
            System.err.println("Failed to insert Elo Score: " + e.getMessage());
        }

        //}
        //catch (Exception e)
        //{
         //    System.out.println("e "+e); 
        //}
    }
    */

    @Transactional
    public void updateEloScores(Integer winnerId, Integer loserId) throws Exception {
        try {
            // Fetch the current Elo scores for both games
            Elo_Score winnerElo = findLatestByGameId(winnerId);
                    //.orElseThrow(() -> new NoSuchElementException("No Elo score found for winnerId: " + winnerId));
            Elo_Score loserElo = findLatestByGameId(loserId);
                    //.orElseThrow(() -> new NoSuchElementException("No Elo score found for loserId: " + loserId));
            
            //Elo_Score winnerElo = winnerEloL.get(winnerEloL.size()-1);
            //Elo_Score loserElo = loserEloL.get(loserEloL.size()-1);


            // Calculate new Elo scores (simplified Elo formula)
            Integer winnerNewElo = calculateNewElo(winnerElo.getElo_Score(), loserElo.getElo_Score(), true);
            Integer loserNewElo = calculateNewElo(loserElo.getElo_Score(), winnerElo.getElo_Score(), false);
    
            if (winnerNewElo == null || loserNewElo == null) {
                throw new NoSuchElementException("No Elo score found for game.");
            }

            // Create new Elo_Score objects with updated scores
            
            System.out.println("attempting to save these");
            Elo_Score newWinnerElo = new Elo_Score();//(winnerId, new Timestamp(System.currentTimeMillis()), winnerNewElo);
            newWinnerElo.setElo_Score(winnerNewElo);
            newWinnerElo.setGame_Id(winnerId);
            newWinnerElo.setTimeStamp(new Timestamp(System.currentTimeMillis()));
            System.out.println("ID before saving: " + newWinnerElo.getId());
            newWinnerElo.setId(null);
            


            Elo_Score newLoserElo = new Elo_Score();//(loserId, new Timestamp(System.currentTimeMillis()), loserNewElo);
            newLoserElo.setElo_Score(loserNewElo);
            newLoserElo.setGame_Id(loserId);
            newLoserElo.setTimeStamp(new Timestamp(System.currentTimeMillis()));
            System.out.println("ID before saving: " + newLoserElo.getId());
            newLoserElo.setId(null);
            
            //entityManager.persist(newWinnerElo);
            //entityManager.persist(newLoserElo);
            
            try {
            entityManager.clear();
            elo_ScoreRepository.save(newWinnerElo);
            entityManager.clear();
            elo_ScoreRepository.save(newLoserElo);
            //elo_ScoreRepository.flush();
            //elo_ScoreRepository.saveAndFlush(newWinnerElo);
            //elo_ScoreRepository.saveAndFlush(newWinnerElo);
            } catch (DataIntegrityViolationException e) {
    // Handle unique constraint violation
    System.out.println("Constraint violation: " + e.getMessage());
}
            
            
            // Save the new Elo scores
            
            //logger.info("Saving new Elo score for game ID: " + newWinnerElo.getGame_Id());
            
            //logger.info("Elo score saved for game ID: " + newWinnerElo.getGame_Id());
            
        } catch (Exception e) {
            throw new RuntimeException("Failinggg to update Elo scores: " + e.getMessage(), e);
        }
    }


    private Integer calEloTest(Integer currentElo, Integer opponentElo, boolean isWinner)
    {
        if (isWinner)
        {
        return (int)(currentElo*1.2);
        }
        else
        {
            return (int)(currentElo*0.8);
        }
    }



    
    private Integer calculateNewElo(Integer currentElo, Integer opponentElo, boolean isWinner) {
        Integer k = 32; // Elo constant
        //double expectedScore = 1.0 / (1.0 + Math.pow(10, (opponentElo - currentElo) / 400.0));
        int score = isWinner ? 1 : 0;

        double Qa = Math.pow(10.0, (currentElo/350));
        double Qb = Math.pow(10.0, (opponentElo/350));
        double expectedScore = Qa/(Qa+Qb);

        return currentElo + (int)(k*(score - expectedScore));
        //return (int) (currentElo + k * ((double)score - expectedScore));
    }

    /*
    public Elo_Score findLatestByGameId(Integer gameId) {
        List<Elo_Score> scores = elo_ScoreRepository.findByGameIdOrderByTimestampDesc(gameId);
        if (scores.isEmpty()) {
            return null;
        }
        return scores.get(0); // Return the most recent Elo score
    }
    */



    public Elo_Score findLatestEloScore(Integer gameId) {
    List<Elo_Score> scores = elo_ScoreRepository.findByGameIdOrderByTimestampDesc(gameId);
    if (scores.isEmpty()) {
        throw new NoSuchElementException("No Elo_Score found for gameId: " + gameId);
    }
    return scores.get(0); 
    }














}
