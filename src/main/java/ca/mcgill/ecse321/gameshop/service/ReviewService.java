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
    public Review createReview(Review.StarRating aRating, String aComment) {
        // check if rating is empty
        if (aRating == null) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Rating cannot be empty");
        }
        Review r = new Review(aRating, aComment, 0);
        r.setReply(r.getReply() != null ? r.getReply() : ""); //if reply is not initialized, server sets it to ""
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
    public Review updateReview(int id, Review.StarRating aRating, String aComment, String aReply) {
        Review r = reviewRepo.findReviewByReviewId(id);

        if (r == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Review with ID " + id + " does not exist."));
        }

        if (aRating == null){
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Rating cannot be empty");
        }

        r.setRating(aRating);
        r.setComment(aComment);
        r.setReply(aReply);
        //r.setReply(r.getReply() != null ? r.getReply() : ""); //if reply is not initialized, server sets it to ""
        //r.setAmountOfLikes(reviewRequest.getAmountOfLikes()); commented out because the user should not be able to change the amount of likes

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

    public Review replyToReview(int id, Review.StarRating aRating, String aComment, int aAmountOfLikes, String reply){
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (reply == null){
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Reply cannot be empty");
        }
        review.setReply(reply);
        return reviewRepo.save(review);
    }
}
