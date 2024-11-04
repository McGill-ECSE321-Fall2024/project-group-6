package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Review;



public class ReviewRequestDto {
    private Review.StarRating rating;
    private String comment;
    private int amountOfLikes;
    private String reply;
    private int customerId;
    private int managerId;
    private int gameId;

    //no review id in request dto
    public ReviewRequestDto() {
    }

    public ReviewRequestDto(Review review) {
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.amountOfLikes = review.getAmountOfLikes();
        this.reply = review.getReply();
        this.customerId = review.getCustomer().getRoleId();
        this.managerId = review.getManager().getRoleId();
        this.gameId = review.getGame().getGameId();
    }

    public Review.StarRating getRating() {
        return rating;
    }

    public void setRating(Review.StarRating rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAmountOfLikes() {
        return amountOfLikes;
    }

    public void setAmountOfLikes(int amountOfLikes) {
        this.amountOfLikes = amountOfLikes;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}

