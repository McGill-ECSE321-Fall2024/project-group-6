package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


    @Transactional
    public Game addGame(String name, String description, float price, int stockQuantity, String photoURL, boolean tobeAdded, Category... allCategories) {
        tobeAdded = true;
        Game game = new Game(name, description, price, stockQuantity, photoURL, tobeAdded, allCategories);
        return gameRepository.save(game);
    }

    @Transactional
    public Game addGameByEmployee(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL, boolean tobeAdded, Category... allCategories) {
        tobeAdded = false;
        Game game = new Game(aName, aDescription, aPrice, aStockQuantity, aPhotoURL, tobeAdded, allCategories);
        return gameRepository.save(game);
    }


    @Transactional
    public Game updateGame(int id, String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL, boolean aToBeAdded, boolean aToBeRemoved, float aPromotion, Category... allCategories) {
        Game game = gameRepository.findGameByGameId(id);

        if (game == null) {
            throw new IllegalArgumentException("Game with ID " + id + " does not exist.");
        }

        game.setPrice(aPrice);
        game.setToBeRemoved(aToBeRemoved);
        game.setPromotion(aPromotion);
        game.setCategories(allCategories);
        game.setName(aName);
        game.setDescription(aDescription);
        game.setStockQuantity(aStockQuantity);
        game.setPhotoURL(aPhotoURL);
        game.setToBeAdded(aToBeAdded);

        return gameRepository.save(game);
    }

    // Get all games
    @Transactional
    public List<Game> getAllGames() {
        List<Game> games = (List<Game>) gameRepository.findAll();
        return games;
    }

    // Find a game by ID
    @Transactional
    public Game getGame(int id) {
        Game gameFromDb = gameRepository.findGameByGameId(id);
        return gameFromDb;
    }

    @Transactional
    public Game getGameByName(String name) {
        List<Game> games = (List<Game>) gameRepository.findAll();

        for (Game game : games) {
            if (Objects.equals(game.getName(), name)) {
                return game;

            }

        }
        return null;

    }

    @Transactional
    public List<Game> getGamesByCategory(String category) {
        List<Game> games = (List<Game>) gameRepository.findAll();
        for (Game game : games) {
            if (!game.getCategories().contains(category)) {
                games.remove(games);

            }

        }
        return games;
    }

    // Delete a game by ID
    @Transactional
    public void deleteGame(int gameId) {
        gameRepository.deleteById(gameId);
    }

    @Transactional
    public boolean approvalToAddGame(int id) {
        Game gameFromDb = gameRepository.findGameByGameId(id);
        if (gameFromDb == null) {
            throw new RuntimeException("The game does not exist in the database");
        } else if (gameFromDb.getToBeAdded()) {
            return true;
        } else {
            return false;
        }

    }

    // Remove Game
    @Transactional
    public boolean approvalToRemoveGame(int id) {
        Game gameFromDb = gameRepository.findGameByGameId(id);
        if (gameFromDb == null) {
            throw new RuntimeException("The game does not exist in the database");
        } else if (gameFromDb.getToBeRemoved()) {
            return true;
        } else {
            return false;
        }

    }
}
