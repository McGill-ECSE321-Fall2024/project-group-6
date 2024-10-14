package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {
    public Game findGameByGameId(int gameId);
}

