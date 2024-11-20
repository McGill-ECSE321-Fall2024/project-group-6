package ca.mcgill.ecse321.gameshop.integration;

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

import ca.mcgill.ecse321.gameshop.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
/**
 * @author Annabelle
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private PaymentRepository repo;

    private static final Review.StarRating VALID_RATING = Review.StarRating.FourStar;
    private static final String VALID_COMMENT = "Amazing Game!";
    private static final int VALID_AMOUNT_OF_LIKES = 0;
    private static final String VALID_REPLY = "";
    private int validId;

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidPayment() {
        // Arrange
        ReviewRequestDto request = new ReviewRequestDto(VALID_RATING, VALID_COMMENT, VALID_AMOUNT_OF_LIKES, VALID_REPLY);

        // Act
        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/review", request, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        this.validId = response.getBody().getReviewId();
        ReviewResponseDto reviewResponse = response.getBody();
        assertEquals(VALID_RATING, reviewResponse.getRating());
        assertEquals(VALID_COMMENT, reviewResponse.getComment());
        assertEquals(VALID_AMOUNT_OF_LIKES, reviewResponse.getAmountOfLikes());
        assertEquals(VALID_REPLY, reviewResponse.getReply());
        assertTrue(reviewResponse.getReviewId() > 0, "Response should have a positive ID.");
    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetReviewByValidId() {
        // Arrange
        String url = String.format("/review/%d", this.validId);

        // Act
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.validId, response.getBody().getReviewId());
        assertEquals(VALID_RATING, response.getBody().getRating());
        assertEquals(VALID_COMMENT, response.getBody().getComment());
        assertEquals(VALID_AMOUNT_OF_LIKES, response.getBody().getAmountOfLikes());
        assertEquals(VALID_REPLY, response.getBody().getReply());
    }

    @Test
    @Order(3)
    public void testGetReviewByInvalidId() {
        // Arrange
        String url = String.format("/review/%d", -1);

        // Act
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(4)
    public void testUpdateReviewByValidId() {
        // Arrange
        Review.StarRating updatedRating = Review.StarRating.ThreeStar;
        String updatedComment = "Actually this is a bad game";
        int updatedAmountOfLikes = 3;
        String updatedReply = "Oh sorry to hear that";

        ReviewRequestDto updatedReviewDto = new ReviewRequestDto(updatedRating, updatedComment, updatedAmountOfLikes, updatedReply);
        String url = String.format("/review/%d", this.validId);

        // Act
        client.put(url, updatedReviewDto);

        // Fetch updated person
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRating, response.getBody().getRating());
        assertEquals(updatedComment, response.getBody().getComment());
        assertEquals(updatedReply, response.getBody().getReply());
        //the equality of the amount of likes is not checked because a user should not be able to change the amount of likes
    }

    @Test
    @Order(5)
    public void testUpdateReviewByInvalidId() {
        // Arrange
        Review.StarRating updatedRating = Review.StarRating.ThreeStar;
        String updatedComment = "Actually this is a bad game";
        int updatedAmountOfLikes = 3;
        String updatedReply = "Oh sorry to hear that";

        String url = String.format("/review/%d", -1);
        ReviewRequestDto updatedReviewDto = new ReviewRequestDto(updatedRating, updatedComment, updatedAmountOfLikes, updatedReply);

        // Act
        ResponseEntity<ReviewResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedReviewDto), ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeletePaymentByValidId() {
        // Arrange
        String url = String.format("/review/%d", this.validId);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<ReviewResponseDto> deletedReview = client.getForEntity(url, ReviewResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedReview.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeletePaymentByInvalidId() {
        // Arrange
        String url = String.format("/review/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
