package ca.mcgill.ecse321.gameshop.integration;

import ca.mcgill.ecse321.gameshop.dto.GameRequestDto;
import ca.mcgill.ecse321.gameshop.dto.GameResponseDto;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class GameIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private GameRepository repo;
    @Autowired
    private CategoryRepository categoryRepo;


    private static final String VALID_NAME = "Subway Surfers";
    private static final String VALID_DESCRIPTION = "A super fun game";
    private static final float VALID_PRICE = 5.5F;
    private static final int VALID_STOCK_QUANTITY = 60;
    private static final String VALID_PHOTOURL = "SubwaySurfersURL";
    private static final boolean toBeAdded = true;
    private static final boolean toBeRemoved = false;
    private static final float VALID_PROMOTION = -5F;

    private static final String INVALID_NAME = "";
    private static final String INVALID_DESCRIPTION = "";
    private static final float INVALID_PRICE = -5.5F;
    private static final int INVALID_STOCK_QUANTITY = -60;
    private static final String INVALID_PHOTOURL = "";
    private int validId;
    private int cat1ID;
    private int cat2ID;

    @AfterAll
    @Transactional
    public void clearDatabase() {
        categoryRepo.deleteAll();
        repo.deleteAll();
    }
   @BeforeAll
    public void setup() {
        // This ensures categories are available for testing
        Category category1 = new Category();
        category1.setCategoryName("Action");
        categoryRepo.save(category1);
        this.cat1ID=category1.getCategoryId();

        Category category2 = new Category();
        category1.setCategoryName("Sports");
        categoryRepo.save(category2);
        this.cat2ID=category2.getCategoryId();
    }

    @Test
    @Order(1)
    public void testCreateValidGame() {
        // Arrange
        //CategoryRequestDto c1 = new CategoryRequestDto("Action");
        //ResponseEntity<CategoryResponseDto> c= client.postForEntity("/categories", c1, CategoryResponseDto.class);

       //List<Category> categoryId = List.of(categoryRepo.findCategoryByCategoryId(c.getBody().getId()));
        //List<Integer> categoryId = List.of(c.getBody().getId());
        List<Integer> categoryId = List.of(this.cat1ID);
        GameRequestDto request = new GameRequestDto(VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_STOCK_QUANTITY, VALID_PHOTOURL, toBeAdded, toBeRemoved, VALID_PROMOTION, categoryId);
        // Act
        ResponseEntity<GameResponseDto> response = client.postForEntity("/employees/games", request, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        this.validId = response.getBody().getGameId();
        System.out.println(this.validId);
        GameResponseDto createdGame = response.getBody();
        assertEquals(VALID_NAME, createdGame.getName());
        assertTrue(createdGame.getGameId() > 0, "Response should have a positive ID.");
    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetGameByValidId() {
        // Arrange
        String url = String.format("/games/id/%d", this.validId);

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
        String url = String.format("/games/id/%d", -1);

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
        List<Integer> categoryId = List.of(this.cat2ID);
        //Game updatedGame = new Game(name, description, price, stock_quantity, photo_url);
        GameRequestDto updatedGameDto = new GameRequestDto(name, description, price, stock_quantity, photo_url, toBeAdded, true, VALID_PROMOTION, categoryId);
        String url = String.format("/games/id/%d", this.validId);

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
        List<Integer> categoryId = List.of(this.cat1ID);

        String url = String.format("/games/id/%d", -1);
        GameRequestDto updatedGameDto = new GameRequestDto(name, description, price, stock_quantity, photo_url, toBeAdded, toBeRemoved, VALID_PROMOTION, categoryId);

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
        String url = String.format("/games/%d", this.validId);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<GameResponseDto> deletedPerson = client.getForEntity(url, GameResponseDto.class);
        //assertEquals(HttpStatus.NOT_FOUND, deletedPerson.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeleteGameByInvalidId() {
        // Arrange
        String url = String.format("/games/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }




}