import ca.mcgill.ecse321.gameshop.dto.GameDTO;
import ca.mcgill.ecse321.gameshop.model.Game;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    // Conversion method from Game to GameDTO
    private GameDTO convertToDTO(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
        gameDTO.setName(game.getName());
        gameDTO.setDescription(game.getDescription());
        gameDTO.setPrice(game.getPrice());
        gameDTO.setStockQuantity(game.getStockQuantity());
        gameDTO.setPhotoURL(game.getPhotoURL());
        gameDTO.setCategory(game.getCategory());
        return gameDTO;
    }

    // Conversion method from GameDTO to Game
    private Game convertToEntity(GameDTO gameDTO) {
        Game game = new Game();
        game.setId(gameDTO.getId());
        game.setName(gameDTO.getName());
        game.setDescription(gameDTO.getDescription());
        game.setPrice(gameDTO.getPrice());
        game.setStockQuantity(gameDTO.getStockQuantity());
        game.setPhotoURL(gameDTO.getPhotoURL());
        game.setCategory(gameDTO.getCategory());
        return game;
    }

    // Create or update a game
    public GameDTO saveGame(GameDTO gameDTO) {
        Game game = convertToEntity(gameDTO);
        Game savedGame = gameRepository.save(game);
        return convertToDTO(savedGame);
    }

    // Get all games
    public List<GameDTO> getAllGames() {
        List<Game> games = (List<Game>) gameRepository.findAll();
        return games.stream().map(this::convertToDTO).toList();
    }

    // Find a game by ID
    public Optional<GameDTO> getGameById(int gameId) {
        return gameRepository.findById(gameId).map(this::convertToDTO);
    }

    public GameDTO getGameByName(String name) {
        Game game = gameRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with name: " + name));
        return new GameDTO(game.getId(), game.getName(), game.getCategory(), game.getPrice());
    }
    
    // Delete a game by ID
    public void deleteGame(int gameId) {
        gameRepository.deleteById(gameId);
    }

    public List<GameDTO> getGamesByCategory(String category) {
        List<Game> games = gameRepository.findByCategory(category);
        return games.stream().map(this::convertToDTO).toList();
    }
}

