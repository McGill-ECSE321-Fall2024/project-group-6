package ca.mcgill.ecse321.gameshop.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gameshop.GameshopApplication;
import ca.mcgill.ecse321.gameshop.dto.GameListDto;
import ca.mcgill.ecse321.gameshop.dto.GameRequestDto;
import ca.mcgill.ecse321.gameshop.dto.GameResponseDto;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameIntegrationTests {
        @Autowired
        private TestRestTemplate client;
        @Autowired
        private GameRepository repo;

        private static final String VALID_NAME = "Minecraft";
        private static final String VALID_DESCRIPTION = "Creative game";
        private static final float VALID_PRICE = 20;
        private static final int STOCK_QUANTITY = 100;
        private static final String VALID_PHOTO = "image url";
        private static final boolean TO_BE_ADDED = false;
        private static final boolean TO_BE_REMOVED = false;
        private static final float VALID_PROMOTION = 30;
        private static final List<Category> CATEGORIES = new ArrayList<>(Arrays.asList(
        new Category("Action"),
        new Category("Adventure"),
        new Category("Puzzle")
        ));
        private int id;

        @AfterAll
        public void clearDatabase() {
                repo.deleteAll();
        }

        @SuppressWarnings("null")
        @Test
        @Order(1)
        public void testCreateValidGame() {
                // Arrange
                GameRequestDto request = new GameRequestDto(VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, STOCK_QUANTITY, VALID_PHOTO, TO_BE_ADDED, TO_BE_REMOVED, VALID_PROMOTION, CATEGORIES);

                // Act
                ResponseEntity<GameResponseDto> response = client.postForEntity("/games", request, GameResponseDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                this.id = response.getBody().getGameId();
                assertEquals(VALID_NAME, response.getBody().getName());
                assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
                assertEquals(VALID_PHOTO, response.getBody().getPhotoURL());
        }

        @Test
        @Order(2)
        public void testGetAllGames() {
                // Arrange
                // Act
                ResponseEntity<GameListDto> response = client.getForEntity("/games", GameListDto.class);
                GameListDto games = response.getBody();

                // Assert
                assertNotNull(games);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(games.getGames());
                assertTrue(!games.getGames().isEmpty(), "There should be at least 1 game returned.");
        }

        @SuppressWarnings("null")
        @Test
        @Order(3)
        public void testGetGameByValidId() {
                // Arrange
                String url = String.format("/games/%d", this.id);

                // Act
                ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(this.id, response.getBody().getGameId());
                assertEquals(VALID_NAME, response.getBody().getName());
                assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
                assertEquals(VALID_PHOTO, response.getBody().getPhotoURL());
        }

        @Test
        @Order(4)
        public void testGetGameByInvalidId() {
                // Arrange
                String url = String.format("/games/%d", -1);

                // Act
                ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @SuppressWarnings("null")
        @Test
        @Order(5)
        public void testUpdateGameByValidId() {
                // Arrange
        String updatedName = "Fifa";
        String updatedDescription = "Football game";
        float updatedPrice = 30;
        int updatedQuantity = 200;
        String updatedPhoto = "football url";
        boolean beAdded = false;
        boolean beRemoved = false;
        float updatedPromotion = 40;
        List<Category> updatedCategories = new ArrayList<>(Arrays.asList(
                new Category("Crime"),
                new Category("Sports")
                ));

                GameRequestDto updatedGameDto = new GameRequestDto(updatedName, updatedDescription, updatedPrice, updatedQuantity, updatedPhoto, beAdded, beRemoved, updatedPromotion, updatedCategories);
                String url = String.format("/games/%d", this.id);

                // Act
                client.put(url, updatedGameDto);
        
                // Fetch updated Game
                ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(updatedName, response.getBody().getName());
                assertEquals(updatedDescription, response.getBody().getDescription());
                assertEquals(updatedPhoto, response.getBody().getPhotoURL());
        }

        @Test
        @Order(6)
        public void testUpdateGameByInvalidId() {
                // Arrange
                String updatedName = "Fifa";
                String updatedDescription = "Football game";
                float updatedPrice = 30;
                int updatedQuantity = 200;
                String updatedPhoto = "football url";
                boolean beAdded = false;
                boolean beRemoved = false;
                float updatedPromotion = 40;
                List<Category> updatedCategories = new ArrayList<>(Arrays.asList(
                new Category("Test"),
                new Category("Dart")
                ));

                String url = String.format("/games/%d", -1);
                GameRequestDto updatedGameDto = new GameRequestDto(updatedName, updatedDescription, updatedPrice, updatedQuantity, updatedPhoto, beAdded, beRemoved, updatedPromotion, updatedCategories);

                // Act
                ResponseEntity<GameResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedGameDto), GameResponseDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @Order(7)
        public void testDeleteGameByValidId() {
                // Arrange
                String url = String.format("/games/%d", this.id);

                // Act
                ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());

                // Verify that the Game was actually deleted by trying to fetch it again
                ResponseEntity<GameResponseDto> deletedGame = client.getForEntity(url, GameResponseDto.class);
                assertEquals(HttpStatus.NOT_FOUND, deletedGame.getStatusCode());
        }

        @Test
        @Order(8)
        public void testDeleteGameByInvalidId() {
        // Arrange
        String url = String.format("/games/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @Order(9)
        public void testCreateInvalidGame() {
                // Arrange
                String updatedName = "Fifa";
                String updatedDescription = "Football game";
                float updatedPrice = 30;
                int updatedQuantity = 200;
                String updatedPhoto = "football url";
                boolean beAdded = false;
                boolean beRemoved = false;
                float updatedPromotion = 40;
                List<Category> updatedCategories = new ArrayList<>(Arrays.asList(
                        new Category("Hunting"),
                        new Category("Fake")
                ));

                GameRequestDto invalidGameDto = new GameRequestDto(updatedName, updatedDescription, updatedPrice, updatedQuantity, updatedPhoto, beAdded, beRemoved, updatedPromotion, updatedCategories);
        
                // Act
                ResponseEntity<String> response = client.postForEntity("/games", invalidGameDto, String.class);
        
                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
}