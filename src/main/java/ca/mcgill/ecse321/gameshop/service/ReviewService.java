package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews(){
        return (List<Review>)reviewRepository.findAll();
    }

    public Review getReviewById(int id) {
        Review review = reviewRepository.findReviewByReviewId(id);
        if (review == null){
            throw new IllegalArgumentException("There is no payment with ID" + id + ".");
        }
        return review;
    }

    public Review createReview(Review.StarRating aRating, String aComment, int aAmountOfLikes) {
        Review newReview = new Review(aRating,aComment,aAmountOfLikes);
        return reviewRepository.save(newReview);
    }

    @Transactional
    public Review updateReview(int id, Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        review.setAmountOfLikes(reviewDetails.getAmountOfLikes());
        review.setReply(reviewDetails.getReply());
        return reviewRepository.save(review);
    }

    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }
}
