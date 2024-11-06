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
    // Conversion method from Game to GameDTO


    @Transactional
    public Game addGame(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL,boolean tobeAdded,Category... allCategories) {
        tobeAdded=true;
        Game game= new Game(aName,aDescription,aPrice,aStockQuantity,aPhotoURL,tobeAdded,allCategories);
        return gameRepository.save(game);
    }
    @Transactional
    public void removeGame(int id) {
        gameRepository.deleteById(id);
    }

    @Transactional
    public Game addPromotion(float promotion, int id) {

        Game gameFromDB= gameRepository.findGameByGameId(id);
        promotion=promotion/100;
        gameFromDB.setPromotion(promotion);
        return gameRepository.save(gameFromDB);
    }

    @Transactional
    public Game removePromotion(int id) {

        Game gameFromDB= gameRepository.findGameByGameId(id);
        //promotion=promotion/100;
        gameFromDB.setPromotion(0);
        return gameRepository.save(gameFromDB);
    }
    // Get all games
    public List<Game> getAllGames() {
        List<Game> games = (List<Game>) gameRepository.findAll();
        return games;
    }

    // Find a game by ID
    public Game getGameById(int id) {
        Game gameFromDb=gameRepository.findGameByGameId(id);
        return gameFromDb;
    }

    public Game getGameByName(String name) {
        List<Game> games = (List<Game>) gameRepository.findAll();

        for (Game game : games) {
            if (Objects.equals(game.getName(), name)) {
                return game;

            }

        }
        return null;

    }

    // Delete a game by ID
    public void deleteGame(int gameId) {
        gameRepository.deleteById(gameId);
    }

    public List<Game> getGamesByCategory(String category) {
        List<Game> games = (List<Game>) gameRepository.findAll();
        for (Game game : games) {
            if (!game.getCategories().contains(category)) {
                game.delete();

            }

        }
        return games;
    }

    /**
     * @author joseph
     * @param id
     * @return
     */
    @Transactional
    public boolean approvalToAddGame(int id) {
        Game gameFromDb=gameRepository.findGameByGameId(id);
        if(gameFromDb==null){
            throw new RuntimeException("The game does not exist in the database");
        }
        else if(gameFromDb.getToBeAdded()){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * @author joseph
     * @param id
     */

    // Remove Game
    @Transactional
    public boolean approvalToRemoveGame(int id) {
        Game gameFromDb=gameRepository.findGameByGameId(id);
        if(gameFromDb==null){
            throw new RuntimeException("The game does not exist in the database");
        }
        else if(gameFromDb.getToBeRemoved()){
            return true;
        }
        else{
            return false;
        }

    }
    @Transactional
    public Game approveToBeAdded(int id) {
        Game gameFromDB= gameRepository.findGameByGameId(id);
        if(gameFromDB==null) {
            throw new RuntimeException("Game does not exist in the database");
        }
        else if(gameFromDB.getToBeAdded()==true) {
            throw new RuntimeException("Game is already added");
        }
        else{
            gameFromDB.setToBeAdded(true);
        }

        return gameRepository.save(gameFromDB);
    }

    @Transactional
    public Game approveToBeDeleted(int id) {
        Game gameFromDB= gameRepository.findGameByGameId(id);
        if(gameFromDB==null) {
            throw new RuntimeException("Game does not exist in the database");
        }
        else if(gameFromDB.getToBeAdded()==false) {
            throw new RuntimeException("Game was never added in the first place");
        }
        else if(gameFromDB.getToBeRemoved()==true){
            throw new RuntimeException("Game was already added");
        }
        else{
            gameFromDB.setToBeRemoved(true);
        }

        return gameRepository.save(gameFromDB);
    }



}

