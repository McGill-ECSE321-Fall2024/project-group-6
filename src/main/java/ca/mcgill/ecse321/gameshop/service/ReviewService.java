package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(int id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(int id, Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        review.setAmountOfLikes(reviewDetails.getAmountOfLikes());
        review.setReply(reviewDetails.getReply());
        return reviewRepository.save(review);
    }

    public void likeReview(int id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setRating(review.getAmountOfLikes() + 1);
    }

    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }
}
