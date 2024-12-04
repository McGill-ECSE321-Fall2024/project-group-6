package ca.mcgill.ecse321.gameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import ca.mcgill.ecse321.gameshop.dto.ReviewListDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Annabelle Huynh-Rondeau
 */

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private ReviewRepository repo;
    @Mock
    private CustomerRepository customerRepo;
    @Mock
    private GameRepository gameRepo;

    @InjectMocks
    private ReviewService service;

    @Test
    public void testCreateReview() {
        // Arrange
        Review.StarRating rating = Review.StarRating.FiveStar;
        String comment = "Amazing experience!";
        Customer customer = new Customer();
        Game game = new Game();

        Review review = new Review(rating, comment, 0);
        when(customerRepo.findCustomerByRoleId(customer.getRoleId())).thenReturn(customer);
        when(gameRepo.findGameByGameId(game.getGameId())).thenReturn(game);
        when(repo.save(any(Review.class))).thenReturn(review);

        // Act
        Review createdReview = service.createReview(rating, comment, customer, game);

        // Assert
        assertNotNull(createdReview);
        assertEquals(rating, createdReview.getRating());
        assertEquals(comment, createdReview.getComment());
        assertEquals(0, createdReview.getAmountOfLikes());
        verify(repo, times(1)).save(any(Review.class));
    }
    @Test
    public void testCreateReviewInvalidStarRating(){
        // Arrange
        Review.StarRating rating = null;
        String comment = "Amazing experience!";
        Customer customer = new Customer();
        Game game = new Game();

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createReview(rating, comment, customer, game);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Rating cannot be empty", exception.getMessage());
    }
    @Test
    public void testCreateReviewWithNotFoundCustomer() {
        // Arrange
        Review.StarRating rating = Review.StarRating.FiveStar;
        String comment = "Great game!";
        Customer customer = new Customer();
        customer.setRoleId(1); // Dummy role ID
        Game game = new Game();
        game.setGameId(1); // Dummy game ID

        when(customerRepo.findCustomerByRoleId(1)).thenReturn(null);

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createReview(rating, comment, customer, game);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Customer associated with this review does not exist", exception.getMessage());
    }

    @Test
    public void testCreateReviewWithNullCustomer() {
        // Arrange
        Review.StarRating rating = Review.StarRating.ThreeStar;
        String comment = "It was okay.";
        Game game = new Game();
        game.setGameId(1); // Dummy game ID

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createReview(rating, comment, null, game);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Customer field is null", exception.getMessage());
    }

    @Test
    public void testCreateReviewWithNotFoundGame() {
        // Arrange
        Review.StarRating rating = Review.StarRating.FourStar;
        String comment = "Enjoyed the graphics!";
        Customer customer = new Customer();
        customer.setRoleId(1); // Dummy role ID
        Game game = new Game();
        game.setGameId(1); // Dummy game ID

        when(customerRepo.findCustomerByRoleId(1)).thenReturn(customer);
        when(gameRepo.findGameByGameId(1)).thenReturn(null);

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createReview(rating, comment, customer, game);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Game associated with this review does not exist", exception.getMessage());
    }

    @Test
    public void testCreateReviewWithNullGame() {
        // Arrange
        Review.StarRating rating = Review.StarRating.OneStar;
        String comment = "Didn't enjoy the game.";
        Customer customer = new Customer();
        customer.setRoleId(1); // Dummy role ID

        when(customerRepo.findCustomerByRoleId(1)).thenReturn(customer);

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createReview(rating, comment, customer, null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Game field is null", exception.getMessage());
    }


    @Test
    public void testUpdateReviewValidId() {
        // Arrange
        int reviewId = 1;
        Review existingReview = new Review(Review.StarRating.ThreeStar, "Okay product", 10);
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(Review.StarRating.FourStar, "Better than expected", 10, "");

        when(repo.findReviewByReviewId(reviewId)).thenReturn(existingReview);
        when(repo.save(any(Review.class))).thenReturn(existingReview);

        // Act
        Review updatedReview = service.updateReview(reviewId, reviewRequestDto.getRating(), reviewRequestDto.getComment(), reviewRequestDto.getReply(), reviewRequestDto.getAmountOfLikes());

        // Assert
        assertNotNull(updatedReview);
        assertEquals(Review.StarRating.FourStar, updatedReview.getRating());
        assertEquals("Better than expected", updatedReview.getComment());
        assertEquals(10, updatedReview.getAmountOfLikes());
        verify(repo, times(1)).save(existingReview);
    }

    @Test
    public void testUpdateReviewInvalidId() {
        // Arrange
        int invalidReviewId = 99;
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(Review.StarRating.TwoStar, "Not good", 0, "");

        when(repo.findReviewByReviewId(invalidReviewId)).thenReturn(null);

        // Act & Assert
        GameShopException e = assertThrows(GameShopException.class, () ->
                service.updateReview(invalidReviewId, reviewRequestDto.getRating(), reviewRequestDto.getComment(), reviewRequestDto.getReply(), reviewRequestDto.getAmountOfLikes())
        );
        assertEquals("Review with ID " + invalidReviewId + " does not exist.", e.getMessage());
        verify(repo, times(0)).save(any(Review.class)); // ensure save is not called
    }

    @Test
    public void testUpdateReviewInvalidRating() {
        //Arrange
        int reviewId = 1;
        Review.StarRating rating = null;
        Review currentReview = new Review(Review.StarRating.TwoStar, "Not good", 10);
        ReviewRequestDto reviewRequest = new ReviewRequestDto(null, "Not good", 10, "");

        when(repo.findReviewByReviewId(reviewId)).thenReturn(currentReview);

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.updateReview(reviewId, reviewRequest.getRating(), reviewRequest.getComment(), reviewRequest.getReply(), reviewRequest.getAmountOfLikes());
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Rating cannot be empty", exception.getMessage());
        verify(repo, times(1)).findReviewByReviewId(reviewId);
    }

    @Test
    public void testGetAllReviews() {
        // Arrange
        Review review1 = new Review(Review.StarRating.FiveStar, "Great product!", 8);
        Review review2 = new Review(Review.StarRating.ThreeStar, "Average experience", 10);
        when(repo.findAll()).thenReturn(Arrays.asList(review1, review2));

        // Act
        List<Review> reviews = (List<Review>) service.getAllReviews();

        // Assert
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        assertEquals("Great product!", reviews.get(0).getComment());
        assertEquals("Average experience", reviews.get(1).getComment());
        assertEquals(8, reviews.get(0).getAmountOfLikes());
        assertEquals(10, reviews.get(1).getAmountOfLikes());
    }

    @Test
    public void testGetReviewById_ValidId() {
        // Arrange
        int reviewId = 1;
        Review review = new Review(Review.StarRating.FourStar, "Good service", 11);
        when(repo.findReviewByReviewId(reviewId)).thenReturn(review);

        // Act
        Review foundReview = service.getReviewById(reviewId);

        // Assert
        assertNotNull(foundReview);
        assertEquals("Good service", foundReview.getComment());
        assertEquals(Review.StarRating.FourStar, foundReview.getRating());
        assertEquals(11, foundReview.getAmountOfLikes());
    }

    @Test
    public void testGetReviewById_InvalidId() {
        // Arrange
        int invalidReviewId = 99;
        when(repo.findReviewByReviewId(invalidReviewId)).thenReturn(null);

        // Act & Assert
        GameShopException e = assertThrows(GameShopException.class, () ->
                service.getReviewById(invalidReviewId)
        );
        assertEquals("Review with ID " + invalidReviewId + " does not exist.", e.getMessage());
    }

    @Test
    public void testDeleteReviewValidId() {
        // Arrange
        int reviewId = 1;
        Review review = new Review(Review.StarRating.FiveStar, "Great product!", 0);

        when(repo.findReviewByReviewId(reviewId)).thenReturn(review);

        // Act
        service.deleteReview(reviewId);

        // Assert
        verify(repo, times(1)).delete(review);
    }

    @Test
    public void testLikeValidReview() {
        // Arrange
        int reviewId = 1; // Assume this ID is generated in the database
        Review review = new Review(Review.StarRating.FourStar, "Great product", 2);
        when(repo.findById(reviewId)).thenReturn(Optional.of(review));

        // Act
        service.likeReview(reviewId);

        // Assert
        assertEquals(3, review.getAmountOfLikes(), "The amount of likes should increase by 1");
        verify(repo, times(1)).findById(reviewId);
        verify(repo, times(1)).save(review); // check if save method was called
    }

    @Test
    public void testLikeInvalidReview() {
        // Arrange
        int nonExistingReviewId = 999;
        when(repo.findById(nonExistingReviewId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.likeReview(nonExistingReviewId);
        });
        assertEquals("Review not found", exception.getMessage());
        verify(repo, times(1)).findById(nonExistingReviewId);
    }

    @Test
    public void testReplyToReviewValidId() {
        // Arrange
        int reviewId = 1;
        Review existingReview = new Review(Review.StarRating.ThreeStar, "Okay product", 10);
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(Review.StarRating.ThreeStar, "Okay product", 10, "Thank you for your feedback!");

        when(repo.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(repo.save(any(Review.class))).thenReturn(existingReview);

        // Act
        Review updatedReview = service.replyToReview(reviewId, Review.StarRating.ThreeStar, "Okay product", 10, "Thank you for your feedback!");

        // Assert
        assertNotNull(updatedReview);
        assertEquals("Thank you for your feedback!", updatedReview.getReply());
        verify(repo, times(1)).save(existingReview);
    }

    @Test
    public void testReplyToReviewInvalidId() {
        // Arrange
        int invalidReviewId = 99;
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(Review.StarRating.ThreeStar, "Okay product", 10, "Thank you for your feedback!");

        when(repo.findById(invalidReviewId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException e = assertThrows(RuntimeException.class, () ->
                service.replyToReview(invalidReviewId, Review.StarRating.ThreeStar, "Okay product", 10, "Thank you for your feedback!")
        );
        assertEquals("Review not found", e.getMessage());
        verify(repo, times(0)).save(any(Review.class));
    }

    @Test
    public void testReplyToReviewNullReply() {
        // Arrange
        int reviewId = 1;
        Review existingReview = new Review(Review.StarRating.TwoStar, "Not good", 10);
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(Review.StarRating.ThreeStar, "Okay product", 10, null);

        when(repo.findById(reviewId)).thenReturn(Optional.of(existingReview));

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.replyToReview(reviewId, Review.StarRating.ThreeStar, "Okay product", 10, null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Reply cannot be empty", exception.getMessage());
        verify(repo, times(0)).save(any(Review.class));
    }
}