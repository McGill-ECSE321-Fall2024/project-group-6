package ca.mcgill.ecse321.gameshop.service;

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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ReviewServiceTests {
    @Mock
    private ReviewRepository repo;
    @InjectMocks
    private ReviewService service;

    private static final Review.StarRating RATING = Review.StarRating.FiveStar;
    private static final String COMMENT = "Great product!";
    private static final int LIKES = 10;
    private static final int ID = 15;

    @Test
    public void testCreateValidReview() {
        // Arrange
        when(repo.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Review createdReview = service.createReview(RATING, COMMENT, LIKES);

        // Assert
        assertNotNull(createdReview);
        assertEquals(RATING, createdReview.getRating());
        assertEquals(COMMENT, createdReview.getComment());
        assertEquals(LIKES, createdReview.getAmountOfLikes());

        // Verify that the save method was called exactly once with the created review
        verify(repo, times(1)).save(createdReview);
    }

    @Test
    public void testGetReviewByValidId() {
        // Arrange
        when(repo.findReviewByReviewId(ID)).thenReturn(new Review(RATING, COMMENT, LIKES));

        // Act
        Review r = service.getReviewById(ID);

        // Assert
        assertNotNull(r);
        assertEquals(RATING, r.getRating());
        assertEquals(COMMENT, r.getComment());
        assertEquals(LIKES, r.getAmountOfLikes());
    }

    @Test
    public void testGetReviewByInvalidId() {
        //Arrange
        //Act
        //Assert
		GameShopException ex = assertThrows(GameShopException.class,
                () -> service.getReviewById(ID));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Review with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testGetAllReviews() {
        // Arrange
        Review review1 = new Review(RATING, COMMENT, LIKES);
        Review review2 = new Review(Review.StarRating.ThreeStar, "Average experience", 5);
        when(repo.findAll()).thenReturn(Arrays.asList(review1, review2));

        // Act
        Iterable<Review> reviewIterable = service.getAllReviews();
        List<Review> reviews = new ArrayList<>();
        reviewIterable.forEach(reviews::add);

        // Assert
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        assertEquals(COMMENT, reviews.get(0).getComment());
        assertEquals("Average experience", reviews.get(1).getComment());
    }

    @Test
    public void testUpdateReviewByValidId() {
        // Arrange
        Review.StarRating updatedRating = Review.StarRating.FourStar;
        String updatedComment = "Better than expected";
        int updatedLikes = 30;

        Review existingReview = new Review(RATING, COMMENT, LIKES);

        when(repo.findReviewByReviewId(ID)).thenReturn(existingReview);

        when(repo.save(any(Review.class))).thenReturn(existingReview);

        // Act
        Review updatedReview = service.updateReview(ID, updatedRating, updatedComment, updatedLikes);

        // Assert
        assertNotNull(updatedReview);
        assertEquals(updatedRating, updatedReview.getRating());
        assertEquals(updatedComment, updatedReview.getComment());
        assertEquals(updatedLikes, updatedReview.getAmountOfLikes());

        // Verify that the save method was called exactly once on the updated review
        verify(repo, times(1)).save(existingReview);
    }

    @Test
    public void testUpdateReviewInvalidId() {
        //Arrange
        //Act
        //Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.updateReview(ID, RATING, COMMENT, LIKES));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Review with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testDeleteReviewByValidId() {
        // Arrange
        Review existingReview = new Review(RATING, COMMENT, LIKES);

        when(repo.findReviewByReviewId(ID)).thenReturn(existingReview);

        // Act
        service.deleteReview(ID);

        // Assert
        verify(repo, times(1)).delete(existingReview);
    }

    @Test
    public void testDeleteReviewInvalidId() {
        //Arrange
        //Act
        //Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.deleteReview(ID));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Review with ID " + ID + " does not exist.", ex.getMessage());
    }
}
