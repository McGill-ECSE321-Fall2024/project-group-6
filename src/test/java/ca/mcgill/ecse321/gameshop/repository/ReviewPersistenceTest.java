
package ca.mcgill.ecse321.gameshop.repository;

import ca.mcgill.ecse321.gameshop.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.mcgill.ecse321.gameshop.repository.GuestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class ReviewPersistenceTest {



    @Autowired
    private ReviewRepository reviewRepo;


    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        reviewRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadReview() {


        Review review = new Review(Review.StarRating.OneStar, "Amazing game", 0);
        review = reviewRepo.save(review);
        Review reviewFromDB = reviewRepo.findReviewByReviewId(review.getReviewId());
        assertNotNull(reviewFromDB);
        assertEquals(reviewFromDB.getAmountOfLikes(),0);
        assertEquals(reviewFromDB.getRating(),Review.StarRating.OneStar);
    }
}