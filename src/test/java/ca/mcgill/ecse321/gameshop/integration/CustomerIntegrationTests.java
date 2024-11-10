package ca.mcgill.ecse321.gameshop.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gameshop.GameshopApplication;
import ca.mcgill.ecse321.gameshop.repository.*;
import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.service.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository customerRepo;

    private int customerId;
    private static final String VALID_NAME = "Jane";
    private static final String VALID_EMAIL = "jane@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "6138548577";
    private static final String VALID_SHIPPING_ADDRESS = "123 Sherbrooke Street";

    @AfterAll
    public void clearDatabase() {
        customerRepo.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void CreateValidCustomer() {
        // Arrange
        CustomerRequestDto request = new CustomerRequestDto(VALID_SHIPPING_ADDRESS, VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_PASSWORD);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customer", request, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.customerId = response.getBody().getCustomerId();
        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
        assertEquals(VALID_SHIPPING_ADDRESS, response.getBody().getShippingAddress());

    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetCustomerByValidId() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.customerId, response.getBody().getCustomerId());
    }

    @SuppressWarnings("null")
    @Test
    @Order(3)
    public void testGetCustomerByInvalidId() {
        // Arrange
        String url = String.format("/customer/%d", -1);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

//    @Test
//    @Order(4)
//    public void testUpdateCustomer() {
//        // Arrange
//        String url = String.format("/customer/%d", this.customerId);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Set up the updated details
//        CustomerRequestDto updatedDetails = new CustomerRequestDto(/* Updated fields here */);
//        HttpEntity<CustomerRequestDto> entity = new HttpEntity<>(updatedDetails, headers);
//
//        // Act
//        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(updatedDetails.getShippingAddress(), response.getBody().getShippingAddress());
//    }

    @Test
    @Order(5)
    public void testDeleteCustomerByValidId() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the Customer was actually deleted by trying to fetch it again
        ResponseEntity<CustomerResponseDto> deletedCustomer = client.getForEntity(url, CustomerResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedCustomer.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteCustomerByInvalidId() {
        // Arrange
        String url = String.format("/customer/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(7)
    public void testCustomerWritesReview() {
        // Arrange
        ReviewRequestDto reviewRequest = new ReviewRequestDto();
        reviewRequest.setRating(Review.StarRating.FIVE);
        reviewRequest.setComment("Great game! Highly recommend.");
        reviewRequest.setAmountOfLikes(0);
        reviewRequest.setReply(null);
        reviewRequest.setCustomerId(this.customerId); // Assuming customerId exists
        reviewRequest.setGameId(1); // ID of purchases game

        // Act
        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/customer/" + this.customerId + "/review", reviewRequest, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Review.StarRating.FIVE, response.getBody().getRating());
        assertEquals("Great game! Highly recommend.", response.getBody().getComment());
        assertEquals(0, response.getBody().getAmountOfLikes());
        assertEquals(this.customerId, response.getBody().getCustomerId());
        assertEquals(1, response.getBody().getGameId());

    }

    @SuppressWarnings("null")
    @Test
    @Order(8)
    public void testCustomerLikesReview() {
        // Arrange
        int reviewId = 2; // ID of the review customer wants to like
        String url = String.format("/customer/%d/likeReview/%d", this.customerId, reviewId);

        // Act
        ResponseEntity<Void> response = client.postForEntity(url, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Fetch review and verify number of likes
        ResponseEntity<ReviewResponseDto> likedReview = client.getForEntity("/review/" + reviewId, ReviewResponseDto.class);
        assertEquals(HttpStatus.OK, likedReview.getStatusCode());
        assertTrue(likedReview.getBody().getAmountofLikes() > 0);
    }

    @SuppressWarnings("null")
    @Test
    @Order(9)
    public void testCustomerEntersPaymentDetailsAtPurchase() {
        // Arrange
        PaymentRequestDto paymentDetails = new PaymentRequestDto();
        paymentDetails.setBillingAddress("123 Main St, Cityville");
        paymentDetails.setCreditCardNb(4111111111111111L);
        paymentDetails.setExpirationDate("12/25");
        paymentDetails.setCvc(123);

        String url = String.format("/customer/%d/purchase", this.customerId);
        HttpEntity<PaymentRequestDto> entity = new HttpEntity<>(paymentDetails);

        // Act
        ResponseEntity<PaymentResponseDto> response = client.postForEntity(url, entity, PaymentResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("123 Main St, Cityville", response.getBody().getBillingAddress());
        assertEquals(this.customerId, response.getBody().getCustomerId());
    }

    @SuppressWarnings("null")
    @Test
    @Order(10)
    public void testCustomerSavesPaymentDetails() {
        // Arrange
        PaymentRequestDto paymentDetails = new PaymentRequestDto();
        paymentDetails.setBillingAddress("123 Main St, Cityville");
        paymentDetails.setCreditCardNb(4111111111111111L);
        paymentDetails.setExpirationDate("12/25");
        paymentDetails.setCvc(123);

        String url = String.format("/customer/%d/savePaymentDetails", this.customerId);
        HttpEntity<PaymentRequestDto> entity = new HttpEntity<>(paymentDetails);

        // Act
        ResponseEntity<PaymentResponseDto> response = client.postForEntity(url, entity, PaymentResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("123 Main St, Cityville", response.getBody().getBillingAddress());
        assertEquals(this.customerId, response.getBody().getCustomerId());
    }

    @SuppressWarnings("null")
    @Test
    @Order(11)
    public void testCustomerAddsGameToCart() {
        // Arrange
        GameRequestDto gameDetails = new GameRequestDto(
                "Test Game",
                "Exciting adventure game",
                59.99f,
                10,
                "http://example.com/game.jpg",
                true,  // toBeAdded
                false, // toBeRemoved
                null,  // promotion
                Category.ACTION
        );

        String url = String.format("/customer/%d/addGameToCart", this.customerId);
        HttpEntity<GameRequestDto> entity = new HttpEntity<>(gameDetails);

        // Act
        ResponseEntity<GameResponseDto> response = client.postForEntity(url, entity, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Game", response.getBody().getName());
        assertTrue(response.getBody().getToBeAdded());
        assertFalse(response.getBody().getToBeRemoved());
    }

    @SuppressWarnings("null")
    @Test
    @Order(12)
    public void testCustomerRemovesGameFromCart() {
        // Arrange
        GameRequestDto gameDetails = new GameRequestDto(
                "Test Game",
                "Exciting adventure game",
                59.99f,
                10,
                "http://example.com/game.jpg",
                false, // toBeAdded
                true,  // toBeRemoved
                null,  // promotion
                Category.ACTION // category
        );

        String url = String.format("/customer/%d/removeGameFromCart", this.customerId);
        HttpEntity<GameRequestDto> entity = new HttpEntity<>(gameDetails);

        // Act
        ResponseEntity<GameResponseDto> response = client.postForEntity(url, entity, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Game", response.getBody().getName());
        assertFalse(response.getBody().getToBeAdded());
        assertTrue(response.getBody().getToBeRemoved());
    }

    @SuppressWarnings("null")
    @Test
    @Order(13)
    public void testAddGameToWishlist() {
        // Create customer and game
        Customer customer = CustomerService.createCustomer("Jane Doe", "456 Aylmer Street");
        Game game = gameService.createGame("Sample Game", "Sample Description", 59.99, 10, "sample-url.com");

        // Add game to wishlist
        CustomerService.addGameToWishlist(customer.getId(), game.getId());

        // Retrieve customer and assert the game was correctly added
        Customer updatedCustomer = CustomerService.getCustomer(customer.getId());
        assertTrue(updatedCustomer.getWishlist().contains(game), "Game should be added to the wishlist");
    }

    @SuppressWarnings("null")
    @Test
    @Order(14)
    public void testRemoveGameFromWishlist() {
        // Create customer and game
        Customer customer = CustomerService.createCustomer("John Doe", "123 Main St");
        Game game = gameService.createGame("Sample Game", "Sample Description", 59.99, 10, "sample-url.com");

        // Add game to wishlist, then remove it
        CustomerService.addGameToWishlist(customer.getId(), game.getId());
        CustomerService.removeGameFromWishlist(customer.getId(), game.getId());

        // Retrieve customer and assert the game was removed
        Customer updatedCustomer = CustomerService.getCustomer(customer.getId());
        assertFalse(updatedCustomer.getWishlist().contains(game), "Game should be removed from the wishlist");
    }
    

}
