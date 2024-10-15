
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
        Random rand = new Random();
        int n = rand.nextInt(1000);

        Review review = new Review(Review.StarRating.OneStar, "Amazing game", 4, n);
        review = reviewRepo.save(review);
        Review reviewFromDB = reviewRepo.findReviewByReviewId(n);
        assertNotNull(reviewFromDB);
        assertEquals(reviewFromDB.getAmountOfLikes(),4);
        assertEquals(reviewFromDB.getRating(),Review.StarRating.OneStar);
    }
}