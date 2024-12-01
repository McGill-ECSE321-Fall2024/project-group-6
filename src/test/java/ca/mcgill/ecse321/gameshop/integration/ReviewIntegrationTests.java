package ca.mcgill.ecse321.gameshop.integration;

import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private GameRepository gameRepo;

    // Constants for valid test data
    private static final String VALID_NAME = "Bob";
    private static final String VALID_EMAIL = "bob@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "+1(514)1234567";
    private static final String VALID_ADDRESS = "123 Sherbrooke West";
    private static final Review.StarRating VALID_RATING = Review.StarRating.FourStar;
    private static final String VALID_COMMENT = "Amazing game!";
    private static final int VALID_AMOUNT_OF_LIKES = 0;
    private static final String VALID_REPLY = "";
    private static final String VALID_GAME_NAME = "Subway Surfers";
    private static final String VALID_DESCRIPTION = "A super fun game!";
    private static final float VALID_PRICE = 5.5F;
    private static final int VALID_STOCK_QUANTITY = 60;
    private static final String VALID_PHOTOURL = "SubwaySurfersURL";
    private static final float VALID_PROMOTION = -5F;

    // IDs for shared data
    private int customerId;
    private int gameId;
    private int reviewId;

    @AfterAll
    public void clearDatabase() {
        reviewRepo.deleteAll();
        customerRepo.deleteAll();
        gameRepo.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateCustomer() {
        CustomerRequestDto request = new CustomerRequestDto(
                VALID_ADDRESS, VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_PASSWORD, null, null
        );

        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request, CustomerResponseDto.class);

        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status OK.");
        assertNotNull(response.getBody(), "Response body should not be null.");

        CustomerResponseDto createdCustomer = response.getBody();
        this.customerId = createdCustomer.getCustomerId();

        assertEquals(VALID_NAME, createdCustomer.getUsername());
        assertEquals(VALID_EMAIL, createdCustomer.getEmail());
        assertEquals(VALID_PHONE, createdCustomer.getPhone());
        assertEquals(VALID_ADDRESS, createdCustomer.getShippingAddress());
    }

    @Test
    @Order(2)
    public void testCreateGame() {
        GameRequestDto request = new GameRequestDto(
                VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_STOCK_QUANTITY,
                VALID_PHOTOURL, true, false, VALID_PROMOTION, List.of()
        );

        ResponseEntity<GameResponseDto> response = client.postForEntity("/employees/games", request, GameResponseDto.class);

        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status OK.");
        assertNotNull(response.getBody(), "Response body should not be null.");

        GameResponseDto createdGame = response.getBody();
        this.gameId = createdGame.getGameId();

        assertEquals(VALID_GAME_NAME, createdGame.getName());
        assertTrue(createdGame.getGameId() > 0, "Game ID should be positive.");
    }

    @Test
    @Order(3)
    public void testCreateReview() {
        String url = String.format("/review/%d/%d", this.customerId, this.gameId);

        ReviewRequestDto request = new ReviewRequestDto(VALID_RATING, VALID_COMMENT, VALID_AMOUNT_OF_LIKES, VALID_REPLY);

        ResponseEntity<ReviewResponseDto> response = client.postForEntity(url, request, ReviewResponseDto.class);

        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status OK.");
        assertNotNull(response.getBody(), "Response body should not be null.");

        ReviewResponseDto createdReview = response.getBody();
        this.reviewId = createdReview.getReviewId();

        assertEquals(VALID_RATING, createdReview.getRating());
        assertEquals(VALID_COMMENT, createdReview.getComment());
        assertEquals(VALID_AMOUNT_OF_LIKES, createdReview.getAmountOfLikes());
    }

    @Test
    @Order(4)
    public void testGetReviewById() {
        String url = String.format("/review/%d", this.reviewId);

        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status OK.");

        ReviewResponseDto fetchedReview = response.getBody();
        assertEquals(this.reviewId, fetchedReview.getReviewId());
        assertEquals(VALID_RATING, fetchedReview.getRating());
        assertEquals(VALID_COMMENT, fetchedReview.getComment());
    }

    @Test
    @Order(5)
    public void testGetReviewByInvalidId() {
        String url = "/review/-1";

        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Expected status NOT_FOUND.");
    }

    @Test
    @Order(6)
    public void testDeleteReview() {
        String url = String.format("/review/%d", this.reviewId);

        ResponseEntity<Void> deleteResponse = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        assertNotNull(deleteResponse, "Response should not be null.");
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode(), "Expected status OK.");

        ResponseEntity<ReviewResponseDto> fetchResponse = client.getForEntity(url, ReviewResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, fetchResponse.getStatusCode(), "Expected status NOT_FOUND after deletion.");
    }
}