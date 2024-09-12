package com.mcvteam.gs_zone.elo_score;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="elo_score")
public class Elo_Score {
    @Id
    @Column(name="id", unique = true)
    private final Integer id;
    private final Integer game_id;
    private final Timestamp timestamp;
    private final Integer elo_score;

    //public Elo_Score()
    //{
    //}

    public Elo_Score(Integer id, Integer game_id, Timestamp timestamp, Integer elo_score)
    {
        this.id = id;
        this.game_id = game_id;
        this.timestamp = timestamp;
        this.elo_score = elo_score;
    }

    public Elo_Score()
    {
        id = -1;
        game_id = -1;
        timestamp = Timestamp.valueOf("2024-09-11 23:33:0.262698");
        elo_score = -1;
    }

    public Integer getId()
    {
        return id;
    }

    public Integer getGame_Id()
    {
        return game_id;
    }

    public Timestamp getTimeStamp()
    {
        return timestamp;
    }

    public Integer getElo_Score()
    {
        return elo_score;
    }


}
