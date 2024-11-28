package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;

public class ReviewRequestDto {
    // Review attributes
    private Review.StarRating rating;
    private String comment;
    private int amountOfLikes;
    private String reply;

    @SuppressWarnings("unused")
    private ReviewRequestDto() {
    }

    public ReviewRequestDto(Review.StarRating aRating, String aComment, int aAmountOfLikes, String aReply) {
        this.rating = aRating;
        this.comment = aComment;
        this.amountOfLikes = aAmountOfLikes;
        this.reply = aReply;

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

    public String getReply() { return reply; }
}