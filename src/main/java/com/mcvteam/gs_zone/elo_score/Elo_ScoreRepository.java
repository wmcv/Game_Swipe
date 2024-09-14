package com.mcvteam.gs_zone.elo_score;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Elo_ScoreRepository extends JpaRepository<Elo_Score, Integer>{
    
    
        void deleteById(int id);
        Optional<Elo_Score> findById(int id);

        //List<Elo_Score> findByGameId(int gameId);

        @Query("SELECT e FROM Elo_Score e WHERE e.game_id = :gameId ORDER BY e.timestamp DESC")
        List<Elo_Score> findByGameIdOrderByTimestampDesc(@Param("gameId") Integer gameId);

        @Query("SELECT e FROM Elo_Score e WHERE e.game_id = :gameId ORDER BY e.timestamp DESC")
        Optional <Elo_Score> findLatestEloScore(@Param("gameId") Integer gameId);

        @Query("SELECT e FROM Elo_Score e WHERE e.game_id = :gameId")
        List<Elo_Score> findAllByGameId(@Param("gameId") Integer gameId);
        
        // @Query("SELECT e FROM Elo_Score e WHERE e.game_id = :game_id ORDER BY e.timestamp DESC")
        // Optional<Elo_Score> findLatestByGameId(@Param("game_id") Integer game_id);

        @Query("SELECT e FROM Elo_Score e WHERE e.game_id = :gameId ORDER BY e.timestamp DESC")
        List<Elo_Score> findLatestByGameId(@Param("gameId") Integer gameId);
        


        


        
}



