package ca.mcgill.ecse321.gameshop.controller;


import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import ca.mcgill.ecse321.gameshop.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.dto.GameListDto;
import ca.mcgill.ecse321.gameshop.dto.GameRequestDto;
import ca.mcgill.ecse321.gameshop.dto.GameResponseDto;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import ca.mcgill.ecse321.gameshop.service.GameService;
/**
 * @author Laurence, Maissa, Joseph and Mario
 */
@RestController
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private CategoryRepository repo;

    @CrossOrigin(origins = "http://localhost:8087")
    @PostMapping("/employees/games")
    public GameResponseDto createGameByEmployee(@RequestBody GameRequestDto g){
        Game game = gameService.addGame(g.getName(), g.getDescription(), g.getPrice(), g.getStockQuantity(), g.getPhotoURL(), g.getCategories());
        return new GameResponseDto(game);

    }

    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/games/id/{id}")
    public GameResponseDto findGameById(@PathVariable int id){
        return new GameResponseDto(gameService.getGame(id));
    }

    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/games")
    public  GameListDto findAllGames(){
        List<GameResponseDto> games = new ArrayList<>();
        for (Game g: gameService.getAllGames()) {
            games.add(new GameResponseDto(g));
        }
        return new GameListDto(games);
    }
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/games/name/{name}")
    public GameResponseDto findGameByName(@PathVariable String name){
        return new GameResponseDto(gameService.getGameByName(name));
    }

    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/games/category/{name}")
    public GameListDto findGamesByCategory(@PathVariable String name){

        List<GameResponseDto> games = new ArrayList<>();
        List<Game>gamesCopy= gameService.getGamesByCategory(name);
        for (Game g: gamesCopy) {
            games.add(new GameResponseDto(g));
        }

        return new GameListDto(games);
    }
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/games/id/{id}")
    public GameResponseDto updateGame(@PathVariable int id, @RequestBody GameRequestDto game) {
        Game g = gameService.updateGame(id,game.getName(),game.getDescription(),game.getPrice(),game.getStockQuantity(),game.getPhotoURL(),game.getToBeAdded(),game.getToBeRemoved(),game.getPromotion(), game.getCategories());

        return new GameResponseDto(g);
    }
    @CrossOrigin(origins = "http://localhost:8087")
    @DeleteMapping("/games/{id}")
    public void deleteGame(@PathVariable int id){
        gameService.deleteGame(id);
    }

    /**
     * Controller method to get all the reviews  of a Game
     * @param id
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/games/{id}/reviews")
    public ReviewListDto getAllGameReviews(@PathVariable int id) {
        List<ReviewResponseDto> reviews = new ArrayList<>();
        for (Review r: gameService.getGameReviews(id)) {
            reviews.add(new ReviewResponseDto(r));
        }
        return new ReviewListDto(reviews);
    }

}