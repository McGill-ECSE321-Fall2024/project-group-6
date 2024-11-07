package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {
    // Inject ReviewService to handle database operations
    @Autowired
    private ReviewRepository reviewRepo;

    // Create a new review and save it in the repository
    @Transactional
    public Review createReview(Review.StarRating aRating, String aComment, int aAmountOfLikes) {
        Review r = new Review(aRating, aComment, aAmountOfLikes);
        return reviewRepo.save(r);
    }

    // Retrieve all reviews from the repository
    public Iterable<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    // Find a review by its id
    public Review getReviewById(int id) {
        Review r = reviewRepo.findReviewByReviewId(id);

        // Throw an exception if no review is found
        if (r == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Review with ID " + id + " does not exist."));
        }

        return r;
    }

    // Update an existing review by ID
    @Transactional
    public Review updateReview(int id, Review.StarRating aRating, String aComment, int aAmountOfLikes) {
        Review r = reviewRepo.findReviewByReviewId(id);

        if (r == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Review with ID " + id + " does not exist."));
        }

        r.setRating(aRating);
        r.setComment(aComment);
        r.setAmountOfLikes(aAmountOfLikes);

        return reviewRepo.save(r);
    }

    // Delete a review by its id
    @Transactional
    public void deleteReview(int id) {
        Review r = reviewRepo.findReviewByReviewId(id);

        if (r == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Review with ID " + id + " does not exist."));
        }

        reviewRepo.delete(r);
    }
}
