package ca.mcgill.ecse321.gameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.mcgill.ecse321.gameshop.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Review;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import ca.mcgill.ecse321.gameshop.service.ReviewService;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private ReviewRepository repo;

    @InjectMocks
    private ReviewService service;

    @Test
    public void testGetAllReviews() {
        // Arrange
        Review review1 = new Review(Review.StarRating.FiveStar, "Great product!", 0);
        Review review2 = new Review(Review.StarRating.ThreeStar, "Average experience", 0);
        when(repo.findAll()).thenReturn(Arrays.asList(review1, review2));

        // Act
        List<Review> reviews = (List<Review>) service.getAllReviews();

        // Assert
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        assertEquals("Great product!", reviews.get(0).getComment());
        assertEquals("Average experience", reviews.get(1).getComment());
    }

    @Test
    public void testGetReviewById_ValidId() {
        // Arrange
        int reviewId = 1;
        Review review = new Review(Review.StarRating.FourStar, "Good service", 0);
        when(repo.findReviewByReviewId(reviewId)).thenReturn(review);

        // Act
        Review foundReview = service.getReviewById(reviewId);

        // Assert
        assertNotNull(foundReview);
        assertEquals("Good service", foundReview.getComment());
        assertEquals(Review.StarRating.FourStar, foundReview.getRating());
        assertEquals(0, foundReview.getAmountOfLikes());
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
    public void testCreateReview() {
        // Arrange
        Review.StarRating rating = Review.StarRating.FiveStar;
        String comment = "Amazing experience!";
        Review review = new Review(rating, comment, 0);
        when(repo.save(any(Review.class))).thenReturn(review);

        // Act
        Review createdReview = service.createReview(rating, comment);

        // Assert
        assertNotNull(createdReview);
        assertEquals(rating, createdReview.getRating());
        assertEquals(comment, createdReview.getComment());
        assertEquals(0, createdReview.getAmountOfLikes());
        verify(repo, times(1)).save(any(Review.class));
    }

    @Test
    public void testUpdateReviewValidId() {
        // Arrange
        int reviewId = 1;
        Review existingReview = new Review(Review.StarRating.ThreeStar, "Okay product", 0);
        Review newReview = new Review(Review.StarRating.FourStar, "Better than expected", 0);
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(newReview);

        when(repo.findReviewByReviewId(reviewId)).thenReturn(existingReview);
        when(repo.save(any(Review.class))).thenReturn(existingReview);

        // Act
        Review updatedReview = service.updateReview(reviewId, reviewRequestDto);

        // Assert
        assertNotNull(updatedReview);
        assertEquals(Review.StarRating.FourStar, updatedReview.getRating());
        assertEquals("Better than expected", updatedReview.getComment());
        assertEquals(0, updatedReview.getAmountOfLikes());
        verify(repo, times(1)).save(existingReview);
    }

    @Test
    public void testUpdateReviewInvalidId() {
        // Arrange
        int invalidReviewId = 99;
        Review review = new Review(Review.StarRating.TwoStar, "Not good", 0);
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(review);

        when(repo.findReviewByReviewId(invalidReviewId)).thenReturn(null);

        // Act & Assert
        GameShopException e = assertThrows(GameShopException.class, () ->
                service.updateReview(invalidReviewId, reviewRequestDto)
        );
        assertEquals("Review with ID " + invalidReviewId + " does not exist.", e.getMessage());
        verify(repo, times(0)).save(any(Review.class)); // ensure save is not called
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
        Review review = new Review(Review.StarRating.FourStar, "Great product", 0);
        when(repo.findById(reviewId)).thenReturn(Optional.of(review));

        // Act
        service.likeReview(reviewId);

        // Assert
        assertEquals(1, review.getAmountOfLikes(), "The amount of likes should increase by 1");
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
}
