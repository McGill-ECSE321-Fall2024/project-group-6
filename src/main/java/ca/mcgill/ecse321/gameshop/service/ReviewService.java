package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.dto.ReviewRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {
    // Inject ReviewService to handle database operations
    @Autowired
    private ReviewRepository reviewRepo;

    // Create a new review and save it in the repository
    @Transactional
    public Review createReview(Review.StarRating aRating, String aComment) {
        // check if rating is empty
        if (aRating == null) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Rating cannot be empty");
        }
        Review r = new Review(aRating, aComment, 0);
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
    public Review updateReview(int id, ReviewRequestDto reviewRequest) {
        Review r = reviewRepo.findReviewByReviewId(id);

        if (r == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Review with ID " + id + " does not exist."));
        }

        r.setRating(reviewRequest.getRating());
        r.setComment(reviewRequest.getComment());
        r.setAmountOfLikes(reviewRequest.getAmountOfLikes());

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

    public Review likeReview(int id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setAmountOfLikes(review.getAmountOfLikes() + 1);
        return reviewRepo.save(review);
    }
}
