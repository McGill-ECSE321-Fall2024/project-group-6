package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ReviewRepository reviewRepo;

    /**
     * @author Maissa
     * Adds a new game to the repository with the specified attributes.
     *
     * @param aName
     * @param aDescription
     * @param aPrice
     * @param aStockQuantity
     * @param aPhotoURL
     * @param allCategories
     * @return Added game
     * @throws GameShopException
     */

    @Transactional
    public Game addGame(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL,List<Integer> allCategories) {

        if(aName==null){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Name cannot be empty."));
        }else if (aDescription==null){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Description cannot be empty."));
        }else if (aPrice<=0){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Price must be over 0.0."));
        }else if (aStockQuantity<=0){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Stock quantity must be over 0.0."));
        }else if (aPhotoURL==null){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Game must have a photo."));
        }else if (allCategories==null){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Game must have at least one category."));
        }

        List<Category> categories = new ArrayList<>();
        for(int i:allCategories){
            categories.add(categoryRepo.findCategoryByCategoryId(i));
        }

        Game game= new Game(aName,aDescription,aPrice,aStockQuantity,aPhotoURL, categories);

        return gameRepository.save(game);
    }

    /**
     * @author Maissa
     * Updates an existing game.
     *
     * @param id
     * @param aName
     * @param aDescription
     * @param aPrice
     * @param aStockQuantity
     * @param aPhotoURL
     * @param aToBeAdded
     * @param tobeRemoved
     * @param aPromotion
     * @param allCategories
     * @return The updated game.
     * @throws GameShopException
     */
    @Transactional
    public Game updateGame(int id,String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL, boolean aToBeAdded, boolean tobeRemoved, float aPromotion, List<Integer> allCategories){
        Game game = gameRepository.findGameByGameId(id);

        if (game== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Game with ID " + id + " does not exist."));
        }else if(aName==null){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Name cannot be empty."));
        }else if (aDescription==null){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Description cannot be empty."));
        }else if (aPrice<=0){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Price must be over 0.0."));
        }else if (aStockQuantity<=0){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Stock quantity must be over 0.0."));
        }else if (aPhotoURL==null){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Game must have a photo."));
        }else if (allCategories==null){
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Game must have at least one category."));
        }
        List<Category> categories = new ArrayList<>();
        for(int i:allCategories){
            categories.add( categoryRepo.findCategoryByCategoryId(i));
        }


        game.setPrice(aPrice);
        game.setToBeRemoved(tobeRemoved);
        game.setPromotion(aPromotion);
        game.setCategories(categories);
        game.setName(aName);
        game.setDescription(aDescription);
        game.setStockQuantity(aStockQuantity);
        game.setPhotoURL(aPhotoURL);
        game.setToBeAdded(aToBeAdded);

        return gameRepository.save(game);
    }
    /**
     * @author Maissa
     * @param
     * @return
     */
    // Get all games
    @Transactional
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        for(Game g:gameRepository.findAll()){
            if (g.getToBeAdded()){
                games.add(g);
            }
        }
        return games;
    }
    /**
     * @author Maissa
     * @param id
     * @return
     */
    // Find a game by ID
    @Transactional
    public Game getGame(int id) {
        if(id<0){throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Game ID "+ id+"is not valid"));}
        else if (gameRepository.findGameByGameId(id) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Game with ID " + id + " does not exist."));
        }
        Game gameFromDb=gameRepository.findGameByGameId(id);
        return gameFromDb;
    }
    /**
     * @author Maissa
     * @param name
     * @return
     */
    @Transactional
    public Game getGameByName(String name) {
        if(name==null){throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Name cannot be empty."));}
        List<Game> games = (List<Game>) gameRepository.findAll();

        for (Game game : games) {
            if (Objects.equals(game.getName(), name)) {
                return game;

            }
        }
        return null;
    }

    /**
     *
     * Joseph
     * @param name
     * @return
     */
    @Transactional
    public List<Game> getGamesByCategory(String name) {
        List<Game> games = new ArrayList<>();
        for (Game game : gameRepository.findAll()) {
            for(int i=0; i<game.getCategories().size();i++){
                if (Objects.equals(game.getCategories().get(i).getCategoryName(), name)) {
                    games.add(game); // add the games with category
                }
            }


        }
        return games;
    }
    /**
     * @author Maissa
     * @param gameId
     * @return
     */

    // Delete a game by ID
    @Transactional
    public void deleteGame(int gameId) {
        Game game = gameRepository.findGameByGameId(gameId);
        if (!this.approvalToRemoveGame(gameId)) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("This game is not authorized to be deleted"));
        } else {
            for (Review r: reviewRepo.findAll()){
                if (r.getGame() == game){
                    reviewRepo.delete(r);
                    r.delete();
                }
            }
            gameRepository.deleteById(gameId);
            game.delete();
        }
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
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("The game does not exist in the database"));
        }
        else {return gameFromDb.getToBeAdded();}
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
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("The game does not exist in the database"));
        } else {return gameFromDb.getToBeRemoved();}
    }

    /**
     * Get all the payment methods of a customer
     * @param id
     * @return
     */
    @Transactional
    public List<Review> getGameReviews(int id) {
        Game game= gameRepository.findGameByGameId(id);
        List<Review> reviews= (List<Review>) reviewRepo.findAll();
        for (int i=0; i<reviews.size();i++){
            if(reviews.get(i).getGame()!=game){
                reviews.remove(reviews.get(i));
            }
        }
        return reviews;
    }


    /*
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

     */
/*
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

 */
    /*
    @Transactional
    public Game removePromotion(int id) {

        Game gameFromDB= gameRepository.findGameByGameId(id);
        //promotion=promotion/100;
        gameFromDB.setPromotion(0);
        return gameRepository.save(gameFromDB);
    }

 */
    /*
    @Transactional
    public Game addPromotion(float promotion, int id) {

        Game gameFromDB= gameRepository.findGameByGameId(id);
        promotion=promotion/100;
        gameFromDB.setPromotion(promotion);
        return gameRepository.save(gameFromDB);
    }

 */



}