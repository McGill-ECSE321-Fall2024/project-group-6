package ca.mcgill.ecse321.gameshop.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.service.GameService;
import ca.mcgill.ecse321.gameshop.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.repository.GameRepositoryRepository;
import org.springframework.web.SpringServletContainerInitializer;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

@SpringBootTest
public class GameUnitTest {
    @Mock
    private GameRepository repo;
    @InjectMocks
    private GameService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidGame() {
        //Arrange
        String name = "Minecraft";
        String description = "A game where you can create worlds";
        float price = 80;
        int stockQuantity = 79;
        String photoURL = "MinecraftURL";
        List<Category> categories = new ArrayList<Category>();

        Game game = new Game(name, description, price, stockQuantity, photoURL);

        //Act
        Game createdGame = service.addGame(name, description, price, stockQuantity, photoURL, true, (Category) categories);

        //Assert
        assertNotNull(createdGame);
        assertEquals(name, createdGame.getName());
        assertEquals(description, createdGame.getDescription());
        assertEquals(price, createdGame.getPrice());
        assertEquals(stockQuantity, createdGame.getStockQuantity());
        assertEquals(photoURL, createdGame.getPhotoURL());
        verify(repo, times(1)).save(game);
    }

    @Test
    public void testReadGameByValidId() {
        //Arrange
        int id = 3;
        String name = "Minecraft";
        String description = "A game where you can create worlds";
        float price = 80;
        int stockQuantity = 79;
        String photoURL = "MinecraftURL";

        Game game = new Game(name, description, price, stockQuantity, photoURL);
        when(repo.findGameByGameId(id)).thenReturn(game);

        //Act
        Game gameToGet = service.getGame(id);

        //Assert
        assertNotNull(gameToGet);
        assertEquals(game.getName(), gameToGet.getName());
        assertEquals(game.getDescription(), gameToGet.getDescription());
        assertEquals(game.getPrice(), gameToGet.getPrice());
        assertEquals(game.getStockQuantity(), gameToGet.getStockQuantity());
        assertEquals(game.getPhotoURL(), gameToGet.getPhotoURL());
    }

    @Test
    public void testReadGameByInvalidId() {
        //Arrange
        int id = 3;
        when(repo.findGameByGameId(3)).thenReturn(null);

        //Act
        //Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->service.getGame(id));
        assertEquals("There is no game with ID " + id + ".", e.getMessage());
        verify(repo, times(1)).findGameByGameId(id);
    }

    @Test
    public void testUpdateGameValidId() {
        // Arrange
        int id = 3;
        String name = "Minecraft";
        String description = "A game where you can create worlds";
        float price = 80;
        int stockQuantity = 79;
        String photoURL = "MinecraftURL";
        List<Category> categories = new ArrayList<>();

        Game game = new Game(name, description, price, stockQuantity, photoURL);
        Game updatedGame = new Game(name, description, 50, 80, photoURL);

        when(repo.findGameByGameId(id)).thenReturn(Optional.of(game));
        when(repo.save(any(Game.class))).thenReturn(updatedGame);

        // Act
        Game updated = service.updateGame(id, name, description, 50, 80, photoURL, false, false, 5, (Category) categories);

        // Assert
        assertNotNull(updated);
        assertEquals(updated.getName(), updatedGame.getName());
        assertEquals(updated.getDescription(), updatedGame.getDescription());
        assertEquals(updated.getPrice(), updatedGame.getPrice());
        assertEquals(updated.getStockQuantity(), updatedGame.getStockQuantity());
        assertEquals(updated.getPhotoURL(), updatedGame.getPhotoURL());
        verify(repo, times(1)).findGameByGameId(id);
        verify(repo, times(1)).save(any(Game.class));
    }

    @Test
    public void testUpdateGameInvalidId() {
        // Arrange
        int invalidId = 99;
        String name = "Minecraft";
        String description = "A game where you can create worlds";
        float price = 80;
        int stockQuantity = 79;
        String photoURL = "MinecraftURL";
        List<Category> categories = new ArrayList<>();

        // Mock findById to return an empty Optional, simulating a non-existent payment
        when(repo.findGameByGameId(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.updateGame(invalidId, name, description, price, stockQuantity, photoURL, false, false, 5, (Category) categories));
        assertEquals("Game not found", exception.getMessage());

        verify(repo, times(1)).findGameByGameId(invalidId);
        verify(repo, times(0)).save(any(Game.class)); // Ensure save is not called
    }

    @Test
    public void testDeleteGame() {
        // Arrange
        int id= 1;
        doNothing().when(repo).deleteGame(id);

        // Act
        service.deleteGame(id);

        // Assert
        verify(repo, times(1)).deleteGame(id);
    }

    @Test
    public void testGetAllRoles() {
        // Arrange
        String name = "Minecraft";
        String description = "A game where you can create worlds";
        float price = 80;
        int stockQuantity = 79;
        String photoURL = "MinecraftURL";
        List<Category> categories = new ArrayList<>();

        Game game1 = new Game(name, description, price, stockQuantity, photoURL);
        Game game2 = new Game("CandyCrush", "Puzzle game", 6, 100, "CandyCrushURL");
        List<Game> games = Arrays.asList(game1, game2);
        when(repo.findAll()).thenReturn(games);

        // Act
        List<Game> allGames = service.getAllGames();

        // Assert
        assertNotNull(allGames);
        assertEquals(2, allGames.size());
        assertEquals(game1, allGames.get(0));
        assertEquals(game2, allGames.get(1));
        verify(repo, times(1)).findAll();
    }
}
