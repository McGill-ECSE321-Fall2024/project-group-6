package ca.mcgill.ecse321.gameshop.integration;

import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
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

/**
 * @author Maissa
 */
public class GameIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private GameRepository repo;
    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private  CustomerRepository customerRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private PersonRepository personRepo;


    private static final String VALID_NAME = "Subway Surfers";
    private static final String VALID_DESCRIPTION = "A super fun game";
    private static final float VALID_PRICE = 5.5F;
    private static final int VALID_STOCK_QUANTITY = 60;
    private static final String VALID_PHOTOURL = "SubwaySurfersURL";
    private static final boolean toBeAdded = true;
    private static final boolean toBeRemoved = false;
    private static final float VALID_PROMOTION = -5F;
    private static final Review.StarRating VALID_RATING = Review.StarRating.FourStar;
    private static final Review.StarRating VALID_RATING2 = Review.StarRating.FiveStar;
    private static final String VALID_COMMENT = "Amazing game!";
    private static final int VALID_AMOUNT_OF_LIKES = 0;
    private static final String VALID_REPLY = "";
    private static final String VALID_C_NAME = "Bob";
    private static final String VALID_C_EMAIL = "bob@mail.mcgill.ca";
    private static final String VALID_C_PASSWORD = "12345678910";
    private static final String VALID_C_PHONE = "+1(514)1234567";
    private static final String VALID_C_ADDRESS = "123 Sherbrooke West";

    private static final String INVALID_NAME = "";
    private static final String INVALID_DESCRIPTION = "";
    private static final float INVALID_PRICE = -5.5F;
    private static final int INVALID_STOCK_QUANTITY = -60;
    private static final String INVALID_PHOTOURL = "";
    private int validId;
    private int validId2;
    private int cat1ID;
    private int cat2ID;
    private int review1Id;
    private int review2Id;
    private int validCustomerId1;
    private int validCustomerId2;


    @AfterAll
    @Transactional
    public void clearDatabase() {
        categoryRepo.deleteAll();
        //repo.deleteAll();
        customerRepo.deleteAll();
        repo.deleteAll();
        reviewRepo.deleteAll();
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

    private String generateUniqueEmail() {
        return "user" + System.currentTimeMillis() + "@mail.com";
    }
    @Test
    @Order(1)
    @Transactional
    public void testCreateCustomer() {
        CustomerRequestDto request = new CustomerRequestDto(
                VALID_C_ADDRESS, VALID_C_NAME, generateUniqueEmail(), VALID_C_PHONE, VALID_C_PASSWORD, null, null
        );

        //Act
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request, CustomerResponseDto.class);

        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status OK.");
        assertNotNull(response.getBody(), "Response body should not be null.");

        CustomerResponseDto createdCustomer = response.getBody();
        this.validCustomerId1 = createdCustomer.getCustomerId();

        assertEquals(VALID_C_NAME, createdCustomer.getUsername());
        assertEquals(request.getEmail(), createdCustomer.getEmail());
        assertEquals(VALID_C_PHONE, createdCustomer.getPhone());
        assertEquals(VALID_C_ADDRESS, createdCustomer.getShippingAddress());
    }
    @Test
    @Order(2)
    @Transactional
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
    @Order(3)
    @Transactional
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
    @Order(4)
    @Transactional
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
    @Order(5)
    @Transactional
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
    @Order(6)
    @Transactional
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
    @Order(7)
    @Transactional
    public void testCreateValidReviews() {
        reviewRepo.deleteAll();
        // Arrange
        String url = String.format("/review/%d/%d", this.validCustomerId1, this.validId); // Use valid customer ID and game ID

        // Create ReviewRequestDto for the review
        ReviewRequestDto request1 = new ReviewRequestDto(VALID_RATING, "Amazing game!", VALID_AMOUNT_OF_LIKES, VALID_REPLY);
        ReviewRequestDto request2 = new ReviewRequestDto(VALID_RATING2, "Amazing game!", VALID_AMOUNT_OF_LIKES, VALID_REPLY);

        // Act
        ResponseEntity<ReviewResponseDto> response1 = client.postForEntity(url, request1, ReviewResponseDto.class);
        ResponseEntity<ReviewResponseDto> response2 = client.postForEntity(url, request2, ReviewResponseDto.class);

        // Assert Review 1
        assertNotNull(response1);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        this.review1Id = response1.getBody().getReviewId();
        ReviewResponseDto createdReview = response1.getBody();
        assertEquals(VALID_RATING, createdReview.getRating());
        assertTrue(createdReview.getReviewId() > 0, "Response should have a positive ID.");

        // Assert Review 2
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        this.review2Id = response2.getBody().getReviewId();
        ReviewResponseDto createdReview2 = response2.getBody();
        assertEquals(VALID_RATING2, createdReview2.getRating());
        assertTrue(createdReview2.getReviewId() > 0, "Response should have a positive ID.");
    }

    @Test
    @Order(8)
    @Transactional
    public void testGetAllGameReviews() {
        // Arrange
        String url = String.format("/games/%d/reviews", this.validId);

        // Act
        ResponseEntity<ReviewListDto> response = client.getForEntity(url, ReviewListDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getReviews().size());

        assertEquals(Review.StarRating.FourStar, response.getBody().getReviews().get(0).getRating());
        assertEquals("Amazing game!", response.getBody().getReviews().get(0).getComment());
        assertEquals(Review.StarRating.FiveStar, response.getBody().getReviews().get(1).getRating());
        assertEquals("Amazing game!", response.getBody().getReviews().get(1).getComment());
    }

    @Test
    @Order(9)
    @Transactional
    public void testDeleteGameByValidId() {
        // Arrange
        String url = String.format("/games/%d", this.validId);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<GameResponseDto> deletedPerson = client.getForEntity(String.format("/games/id/%d", this.validId), GameResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedPerson.getStatusCode());
    }

    @Test
    @Order(10)
    @Transactional
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