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




        /*
        @Query(value = "WITH latest_editions AS (" +
        "    SELECT e.game_id, MAX(e.timestamp) AS latest_timestamp " +
        "    FROM elo_score e " +
        "    GROUP BY e.game_id " +
        "), latest_games AS (" +
        "    SELECT e.game_id, e.elo_score " +
        "    FROM elo_score e " +
        "    JOIN latest_editions le ON e.game_id = le.game_id " +
        "    AND e.timestamp = le.latest_timestamp " +
        ") " +
        "SELECT e.game_id, e.elo_score " +
        "FROM latest_games e " +
        "ORDER BY e.elo_score DESC " +
        "LIMIT 10", nativeQuery = true)
        List<Integer[]> findTop10GamesByEloScore();
        */

        @Query(value = "WITH latest_editions AS (" +
            "    SELECT e.game_id, MAX(e.timestamp) AS latest_timestamp " +
            "    FROM elo_score e " +
            "    GROUP BY e.game_id " +
            "), latest_games AS (" +
            "    SELECT e.game_id, e.elo_score " +
            "    FROM elo_score e " +
            "    JOIN latest_editions le ON e.game_id = le.game_id " +
            "    AND e.timestamp = le.latest_timestamp " +
            ") " +
            "SELECT g.name, lg.elo_score " +
            "FROM latest_games lg " +
            "JOIN games g ON lg.game_id = g.id " +
            "ORDER BY lg.elo_score DESC " +
            "LIMIT 10", nativeQuery = true)
            List<Object[]> findTop10GamesByEloScore();


        


        
}



