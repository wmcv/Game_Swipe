package com.mcvteam.gs_zone.game;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
    }


    public List<Game> getGames()
    {
        return gameRepository.findAll();
    }


    public List<Game> getGameByName(String searchText)
    {
        return gameRepository.findAll().stream()
                    .filter(game -> game.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());
    }

    public List<Game> getGameById(Integer searchId)
    {
        return gameRepository.findAll().stream()
                    .filter(game -> 
                    game.getId().equals(searchId))
                    .collect(Collectors.toList());
    }


    public Map<Integer, Game> getGamesById(Set<Integer> ids) {
        List<Game> games = gameRepository.findAllById(ids);
        return games.stream().collect(Collectors.toMap(Game::getId, game -> game));
    }

    /*public List<Game> getRandomGames(int count) {
        List<Game> allGames = gameRepository.findAll();
        if (allGames.size() <= count) {
            return allGames; // If there are fewer games than the count, return all of them
        }
        Random random = new Random();
        return random.ints(0, allGames.size())
                     .distinct()
                     .limit(count)
                     .mapToObj(allGames::get)
                     .collect(Collectors.toList());
}*/


    






}
