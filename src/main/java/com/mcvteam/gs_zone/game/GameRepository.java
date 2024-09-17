package com.mcvteam.gs_zone.game;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mcvteam.gs_zone.elo_score.Elo_Score;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{
    void deleteById(int id);
    Optional<Game> findById(int id);




    @Query("SELECT e FROM Elo_Score e WHERE e.game_id = :gameId ORDER BY e.timestamp DESC")
    List<Elo_Score> findByGameIdOrderByTimestampDesc(@Param("gameId") Integer gameId);


}
