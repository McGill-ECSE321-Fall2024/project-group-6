package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class ReviewsResponseDto {
    private List<ReviewResponseDto> reviews;

    public ReviewsResponseDto(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }
}
