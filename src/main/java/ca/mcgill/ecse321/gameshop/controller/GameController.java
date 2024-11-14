package ca.mcgill.ecse321.gameshop.controller;


import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import ca.mcgill.ecse321.gameshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Laurence, Maissa, Joseph and Mario
 */
@RestController
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private CategoryRepository repo;

    @PostMapping("/employees/games")
    public GameResponseDto createGameByEmployee(@RequestBody GameRequestDto g){
        Game game = gameService.addGame(g.getName(), g.getDescription(), g.getPrice(), g.getStockQuantity(), g.getPhotoURL(), g.getCategories());
        return new GameResponseDto(game);

    }


    @GetMapping("/games/id/{id}")
    public GameResponseDto findGameById(@PathVariable int id){
        return new GameResponseDto(gameService.getGame(id));
    }
    @GetMapping("/games")
    public  GameListDto findAllGames(){
        List<GameResponseDto> games = new ArrayList<>();
        for (Game g: gameService.getAllGames()) {
            games.add(new GameResponseDto(g));
        }
        return new GameListDto(games);
    }
    @GetMapping("/games/name/{name}")
    public GameResponseDto findGameByName(@PathVariable String name){
        return new GameResponseDto(gameService.getGameByName(name));
    }
    @GetMapping("/games/category/{category}")
    public GameListDto findGamesByCategory(@PathVariable Category category){
        List<GameResponseDto> games = new ArrayList<>();
        List<Game>gamesCopy= gameService.getGamesByCategory(category);
        for (Game g: gamesCopy) {
            games.add(new GameResponseDto(g));
        }
        return new GameListDto(games);
    }

    @PutMapping("/games/id/{id}")
    public GameResponseDto updateGame(@PathVariable int id, @RequestBody GameRequestDto game) {
        Game g = gameService.updateGame(id,game.getName(),game.getDescription(),game.getPrice(),game.getStockQuantity(),game.getPhotoURL(),game.getToBeAdded(),game.getToBeRemoved(),game.getPromotion(), game.getCategories());

        return new GameResponseDto(g);
    }

    @DeleteMapping("/games/{id}")
    public void deleteGame(@PathVariable int id){
        gameService.deleteGame(id);
    }


}