package com.mcvteam.gs_zone.game;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="games")
public class Game {

    @Id
    @Column(name="id", unique = true)
    private Integer id;
    private String image_url;
    private String name;


    public Game(Integer id, String image_url, String name)
    {
        this.id = id;
        this.image_url = image_url;
        this.name = name;
    }

    public Game()
    {

    }


    public Integer getId()
    {
        return id;
    }

    public String getImage_Url()
    {
        return image_url;
    }

    public String getName()
    {
        return name;
    }


    
}

