package com.mcvteam.gs_zone.elo_score;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="elo_score")
public class Elo_Score {
    
    //@Transient
    @Column(name = "game_id")
    private Integer game_id;
    @Column(name = "timestamp", unique = true)
    private Timestamp timestamp;
    //@Transient
    @Column(name = "elo_score")
    private Integer elo_score;



    @Id
    @Column(name="id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    

    //public Elo_Score()
    //{
    //}

    public Elo_Score(Integer game_id, Timestamp timestamp, Integer elo_score)
    {
        this.game_id = game_id;
        this.timestamp = timestamp;
        this.elo_score = elo_score;
    }

    public Elo_Score()
    {
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
    
    public void setId(Integer id)
    {
        this.id = id;
    }


    public void setGame_Id(Integer game_id)
    {
        this.game_id = game_id;
    }

    public void setTimeStamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }

    public void setElo_Score(Integer elo_score)
    {
        this.elo_score = elo_score;
    }

}
