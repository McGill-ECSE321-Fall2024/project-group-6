package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;

public class ReviewResponseDto {
    // Review attributes
    private Review.StarRating rating;
    private String comment;
    private int amountOfLikes;
    private int reviewId;
    private String reply;


    public ReviewResponseDto() {
    }

    public ReviewResponseDto(Review review) {
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.amountOfLikes = review.getAmountOfLikes();
        this.reviewId = review.getReviewId();
        this.reply = review.getReply();
    }

    public Review.StarRating getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public int getAmountOfLikes() {
        return amountOfLikes;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getReply() {
        return reply;
    }
}

