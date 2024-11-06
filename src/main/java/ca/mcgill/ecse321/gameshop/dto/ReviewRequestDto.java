package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Review;
import jakarta.validation.constraints.NotBlank;

public class ReviewRequestDto {
    // Review attributes
    @NotBlank(message="Rating is required.")
    private Review.StarRating rating;
    @NotBlank(message="Comment is required.")
    private String comment;
    @NotBlank(message="Likes are required.")
    private int amountOfLikes;

    @SuppressWarnings("unused")
    public ReviewRequestDto() {
    }

    public ReviewRequestDto(Review.StarRating aRating, String aComment, int aAmountOfLikes) {
        this.rating = aRating;
        this.comment = aComment;
        this.amountOfLikes = aAmountOfLikes;
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
}