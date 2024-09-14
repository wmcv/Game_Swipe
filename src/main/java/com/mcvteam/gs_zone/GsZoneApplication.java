package com.mcvteam.gs_zone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class GsZoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsZoneApplication.class, args);
    }
}

//table can only hold two elements of each game?


//add way to grab id numbers from the 2RandomGames method and pass
//them to the js file into gId1/gId2

//integrate score system
//needs the elo_score of each game before the click
//creates new one for each after the click




//add stats/ranks, maybe in hamburger menu?

