package ca.mcgill.ecse321.gameshop.controller;


import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/games")
    public GameResponseDTO createGameByManager(@RequestBody GameResponseDTO g){
        Game game = gameService.addGame(g.getName(),g.getDescription(),g.getPrice(),g.getStockQuantity(),g.getPhotoURL(),g.getToBeAdded(), (Category) g.getCategories());
        return  new GameResponseDTO(game);
    }
    @PostMapping("/employees/games")
    public GameResponseDTO createGameByEmployee(@RequestBody GameResponseDTO g){

        Game game = gameService.addGameByEmployee(g.getName(), g.getDescription(), g.getPrice(), g.getStockQuantity(), g.getPhotoURL(), g.getToBeAdded(), (Category) g.getCategories());
        return new GameResponseDTO(game);

    }


    @GetMapping("/games/{id}")
    public GameResponseDTO findGameById(@PathVariable int id){
        return new GameResponseDTO(gameService.getGame(id));
    }
    @GetMapping("/games")
    public  GamesResponseDTO findAllGames(){
        List<GameResponseDTO> games = new ArrayList<>();
        for (Game g: gameService.getAllGames()) {
            games.add(new GameResponseDTO(g));
        }
        return new GamesResponseDTO(games);
    }
    @GetMapping("/games/{name}")
    public GameResponseDTO findGameByName(@PathVariable String name){
        return new GameResponseDTO(gameService.getGameByName(name));
    }
    @GetMapping("/games/{category}")
    public GamesResponseDTO findGamesByCategory(@PathVariable String category){
        List<GameResponseDTO> games = new ArrayList<>();
        List<Game>gamesCopy= gameService.getGamesByCategory(category);
        for (Game g: gamesCopy) {
            games.add(new GameResponseDTO(g));
        }
        return new GamesResponseDTO(games);
    }

    @PutMapping("/games/{id}")
    public GameResponseDTO updateEmployee(@PathVariable int id, @RequestBody GameRequestDTO game) {
        Game g = gameService.updateGame(id,game.getName(),game.getDescription(),game.getPrice(),game.getStockQuantity(),game.getPhotoURL(),game.getToBeAdded(),game.getToBeRemoved(),game.getPromotion(),(Category) game.getCategories());

        return new GameResponseDTO(g);
    }

    @DeleteMapping("/games/{id}")
    public void deleteGame(@PathVariable int id){
        gameService.deleteGame(id);
    }
}
