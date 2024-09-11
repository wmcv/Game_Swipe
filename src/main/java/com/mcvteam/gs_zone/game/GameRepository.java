package com.mcvteam.gs_zone.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{
    void deleteById(int id);
    Optional<Game> findById(int id);
}
