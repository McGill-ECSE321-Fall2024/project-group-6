import ca.mcgill.ecse321.gameshop.dto.GameDTO;
import ca.mcgill.ecse321.gameshop.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    // Create a new game
    @PostMapping
    public GameDTO createGame(@RequestBody GameDTO gameDTO) {
        return gameService.saveGame(gameDTO);
    }

    // Update an existing game
    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable Integer id, @RequestBody GameDTO gameDetails) {
        return gameService.getGameById(id)
                .map(existingGame -> {
                    gameDetails.setId(id);  // Ensure the ID is set for update
                    return ResponseEntity.ok(gameService.saveGame(gameDetails));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a game
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id) {
        if (gameService.getGameById(id).isPresent()) {
            gameService.deleteGame(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get all games
    @GetMapping
    public List<GameDTO> getAllGames() {
        return gameService.getAllGames();
    }

    // Get a game by ID
    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Integer id) {
        return gameService.getGameById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GameDTO> getGameByName(@PathVariable String name) {
        GameDTO gameDTO = gameService.getGameByName(name);
        return ResponseEntity.ok(gameDTO);
    }

    // Get games by category
    @GetMapping("/category")
    public List<GameDTO> getGamesByCategory(@RequestParam String category) {
        return gameService.getGamesByCategory(category);
    }
}

