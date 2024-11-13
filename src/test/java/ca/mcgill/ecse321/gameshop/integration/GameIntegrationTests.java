package ca.mcgill.ecse321.gameshop.integration;

import java.util.*;

import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
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

import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.dto.GameRequestDto;
import ca.mcgill.ecse321.gameshop.dto.GameResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class GameIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private GameRepository repo;

    private static final String VALID_NAME = "Subway Surfers";
    private static final String VALID_DESCRIPTION = "A super fun game";
    private static final float VALID_PRICE = 5.5F;
    private static final int VALID_STOCK_QUANTITY = 60;
    private static final String VALID_PHOTOURL = "SubwaySurfersURL";
    private static final boolean toBeAdded = true;
    private static final boolean toBeRemoved = false;
    private static final float VALID_PROMOTION = -5F;
    private static final Category category1 = new Category("mobile");
    private static final Category category2 = new Category("relaxing");
    private static final List<Category> categories = Arrays.asList(category1, category2);

    private static final String INVALID_NAME = "";
    private static final String INVALID_DESCRIPTION = "";
    private static final float INVALID_PRICE = -5.5F;
    private static final int INVALID_STOCK_QUANTITY = -60;
    private static final String INVALID_PHOTOURL = "";
    private int validId;

    @Test
    @Order(1)
    public void testCreateValidGame() {
        // Arrange
        GameRequestDto request = new GameRequestDto(VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_STOCK_QUANTITY, VALID_PHOTOURL, toBeAdded, toBeRemoved, VALID_PROMOTION, category1, category2);

        // Act
        ResponseEntity<GameResponseDto> response = client.postForEntity("/game", request, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        this.validId = response.getBody().getGameId();
        GameResponseDto createdGame = response.getBody();
        assertEquals(VALID_NAME, createdGame.getName());
        assertTrue(createdGame.getGameId() > 0, "Response should have a positive ID.");
    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetGameByValidId() {
        // Arrange
        String url = String.format("/game/%d", this.validId);

        // Act
        ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.validId, response.getBody().getGameId());
        assertEquals(VALID_NAME, response.getBody().getName());
    }

    @Test
    @Order(3)
    public void testGetGameByInvalidId() {
        // Arrange
        String url = String.format("/game/%d", -1);

        // Act
        ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(4)
    public void testUpdateGameByValidId() {
        // Arrange
        String name = "Subway Surfers 2";
        String description = "new version of subway surfers";
        float price = 9.5F;
        int stock_quantity = 70;
        String photo_url = "subway_surfers2URL";

        Game updatedGame = new Game(name, description, price, stock_quantity, photo_url);
        GameRequestDto updatedGameDto = new GameRequestDto(name, description, price, stock_quantity, photo_url, toBeAdded, toBeRemoved, VALID_PROMOTION, category1, category2);
        String url = String.format("/game/%d", this.validId);

        // Act
        client.put(url, updatedGameDto);

        // Fetch updated person
        ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(name, response.getBody().getName());
    }

    @Test
    @Order(5)
    public void testUpdateGameByInvalidId() {
        // Arrange
        String name = "Subway Surfers 2";
        String description = "new version of subway surfers";
        float price = 9.5F;
        int stock_quantity = 70;
        String photo_url = "subway_surfers2URL";

        String url = String.format("/game/%d", -1);
        GameRequestDto updatedGameDto = new GameRequestDto(name, description, price, stock_quantity, photo_url, toBeAdded, toBeRemoved, VALID_PROMOTION, category1, category2);

        // Act
        ResponseEntity<GameResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedGameDto), GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteGameByValidId() {
        // Arrange
        String url = String.format("/game/%d", this.validId);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<GameResponseDto> deletedPerson = client.getForEntity(url, GameResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedPerson.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeleteGameByInvalidId() {
        // Arrange
        String url = String.format("/game/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
