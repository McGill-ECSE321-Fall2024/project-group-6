package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class ReviewListDto {
    private List<ReviewResponseDto> reviews;

    public ReviewListDto() {
    }
    public ReviewListDto(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }
}
