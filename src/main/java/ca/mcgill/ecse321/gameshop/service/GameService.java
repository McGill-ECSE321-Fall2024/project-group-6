package ca.mcgill.ecse321.gameshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import jakarta.transaction.Transactional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public Game addGame(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL) {
        if (aName == null) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("Name cannot be empty."));
        }
        
        else if (aDescription == null){
            throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("Description cannot be empty."));
        }
        
        else if (aPrice <= 0){
            throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("Price must be over 0.0."));
        }
        
        else if (aStockQuantity <= 0) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("Stock quantity must be over 0.0."));
        }
        
        else if (aPhotoURL == null) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("Game must have a photo."));
        }

        Game game= new Game(aName, aDescription, aPrice, aStockQuantity, aPhotoURL);

        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(int id, String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL, boolean aToBeAdded, boolean tobeRemoved, float aPromotion) {
        Game game = gameRepository.findGameByGameId(id);

        if (game == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Game with ID " + id + " does not exist."));
        }
        
        else if(aName == null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Name cannot be empty."));
        }
        
        else if (aDescription == null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Description cannot be empty."));
        }

        else if (aPrice <= 0){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Price must be over 0.0."));
        }
        
        else if (aStockQuantity <= 0){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Stock quantity must be over 0.0."));
        }
        
        else if (aPhotoURL == null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Game must have a photo."));
        }

        game.setPrice(aPrice);
        game.setToBeRemoved(tobeRemoved);
        game.setPromotion(aPromotion);
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
        List<Game> games = new ArrayList<>();

        for (Game g : gameRepository.findAll()) {
            if (g.getToBeAdded()) {
                games.add(g);
            }
        }

        return games;
    }

    // Find a game by ID
    @Transactional
    public Game getGame(int id) {
        if (id < 0) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Game ID "+ id + "is not valid."));
        }

        else if (gameRepository.findGameByGameId(id) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Game with ID " + id + " does not exist."));
        }

        Game gameFromDb = gameRepository.findGameByGameId(id);

        return gameFromDb;
    }

    @Transactional
    public Game getGameByName(String name) {
        if (name == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Name cannot be empty."));
        }

        List<Game> games = (List<Game>) gameRepository.findAll();

        for (Game game : games) {
            if (Objects.equals(game.getName(), name)) {
                return game;
            }
        }

        return null;
    }

    @Transactional
    public List<Game> getGamesByCategory(Category category) {
        List<Game> games = new ArrayList<>();

        for (Game game : gameRepository.findAll()) {
            if (game.getCategories().contains(category)) {
                games.add(game);
            }
        }

        return games;
    }

    @Transactional
    public void deleteGame(int gameId) {
        if (!this.approvalToRemoveGame(gameId)) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("This game is not authorized to be deleted"));
        }
        
        else {
            gameRepository.deleteById(gameId);
        }
    }

    /**
     * @author joseph
     * @param id
     * @return
     */
    @Transactional
    public boolean approvalToAddGame(int id) {
        Game gameFromDb = gameRepository.findGameByGameId(id);

        if (gameFromDb == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The game does not exist in the database"));
        }

        else {
            return gameFromDb.getToBeAdded();
        }
    }

    /**
     * @author joseph
     * @param id
     */
    // Remove Game
    @Transactional
    public boolean approvalToRemoveGame(int id) {
        Game gameFromDb = gameRepository.findGameByGameId(id);

        if(gameFromDb == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("The game does not exist in the database"));
        }
        
        else {
            return gameFromDb.getToBeRemoved();
        }
    }
}