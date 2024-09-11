package com.mcvteam.gs_zone.elo_score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcvteam.gs_zone.game.Optional;

@Repository
public interface Elo_ScoreRepository extends JpaRepository<Elo_Score, Integer>{
    
    
        void deleteById(int id);
        Optional<Elo_Score> findById(int id);
}