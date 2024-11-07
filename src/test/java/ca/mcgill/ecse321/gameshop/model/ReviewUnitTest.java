package ca.mcgill.ecse321.gameshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import ca.mcgill.ecse321.gameshop.service.ReviewService;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class ReviewUnitTest {

    @Mock
    private ReviewRepository repo;

    @InjectMocks
    private ReviewService service;

    @Test
    public void testGetAllReviews() {
        // Arrange
        Review review1 = new Review(Review.StarRating.FiveStar, "Great product!", 10);
        Review review2 = new Review(Review.StarRating.ThreeStar, "Average experience", 5);
        when(repo.findAll()).thenReturn(Arrays.asList(review1, review2));

        // Act
        List<Review> reviews = service.getAllReviews();

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
        Review review = new Review(Review.StarRating.FourStar, "Good service", 8);
        when(repo.findReviewByReviewId(reviewId)).thenReturn(review);

        // Act
        Review foundReview = service.getReviewById(reviewId);

        // Assert
        assertNotNull(foundReview);
        assertEquals("Good service", foundReview.getComment());
        assertEquals(Review.StarRating.FourStar, foundReview.getRating());
        assertEquals(8, foundReview.getAmountOfLikes());
    }

    @Test
    public void testGetReviewById_InvalidId() {
        // Arrange
        int invalidReviewId = 99;
        when(repo.findReviewByReviewId(invalidReviewId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                service.getReviewById(invalidReviewId)
        );
        assertEquals("There is no payment with ID" + invalidReviewId + ".", exception.getMessage());
    }

    @Test
    public void testCreateReview() {
        // Arrange
        Review.StarRating rating = Review.StarRating.FiveStar;
        String comment = "Amazing experience!";
        int likes = 15;
        Review review = new Review(rating, comment, likes);
        when(repo.save(any(Review.class))).thenReturn(review);

        // Act
        Review createdReview = service.createReview(rating, comment, likes);

        // Assert
        assertNotNull(createdReview);
        assertEquals(rating, createdReview.getRating());
        assertEquals(comment, createdReview.getComment());
        assertEquals(likes, createdReview.getAmountOfLikes());
        verify(repo, times(1)).save(any(Review.class));
    }

    @Test
    public void testUpdateReview_ValidId() {
        // Arrange
        int reviewId = 1;
        Review existingReview = new Review(Review.StarRating.ThreeStar, "Okay product", 3);
        Review updatedDetails = new Review(Review.StarRating.FourStar, "Better than expected", 6);

        when(repo.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(repo.save(any(Review.class))).thenReturn(existingReview);

        // Act
        Review updatedReview = service.updateReview(reviewId, updatedDetails);

        // Assert
        assertNotNull(updatedReview);
        assertEquals(Review.StarRating.FourStar, updatedReview.getRating());
        assertEquals("Better than expected", updatedReview.getComment());
        assertEquals(6, updatedReview.getAmountOfLikes());
        verify(repo, times(1)).save(existingReview);
    }

    @Test
    public void testUpdateReview_InvalidId() {
        // Arrange
        int invalidReviewId = 99;
        Review updatedDetails = new Review(Review.StarRating.TwoStar, "Not good", 1);

        when(repo.findById(invalidReviewId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.updateReview(invalidReviewId, updatedDetails)
        );
        assertEquals("Review not found", exception.getMessage());
        verify(repo, times(0)).save(any(Review.class)); // Ensure save is not called
    }

    @Test
    public void testDeleteReview_ValidId() {
        // Arrange
        int reviewId = 1;

        // Act
        service.deleteReview(reviewId);

        // Assert
        verify(repo, times(1)).deleteById(reviewId);
    }
}
