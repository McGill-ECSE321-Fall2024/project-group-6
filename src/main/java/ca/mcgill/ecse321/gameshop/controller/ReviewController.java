package ca.mcgill.ecse321.gameshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.service.ReviewService;
import ca.mcgill.ecse321.gameshop.dto.ReviewListDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewResponseDto;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    /**
     * Create the new review
     *
     * @param review The review to create.
     * @return The new review.
     */
    @PostMapping("/review")
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto review) {
        Review r = reviewService.createReview(review.getRating(), review.getComment());
        return new ReviewResponseDto(r);
    }

    /**
     * Return all reviews.
     *
     * @return All the reviews.
     */
    @GetMapping("/review")
    public ReviewListDto getAllReviews() {
        List<ReviewResponseDto> reviews = new ArrayList<>();

        for (Review r : reviewService.getAllReviews()){
            reviews.add(new ReviewResponseDto(r));
        }

        return new ReviewListDto(reviews);
    }


    /**
     * Return the review with the given ID.
     *
     * @param id The primary key of the person to find.
     * @return The review with the given ID.
     */
    @GetMapping("/review/{id}")
    public ReviewResponseDto getReviewById(@PathVariable int id) {
        Review r = reviewService.getReviewById(id);
        return new ReviewResponseDto(r);
    }

    /**
     * Update the review with the given ID.
     *
     * @param id the ID of the review
     * @return The updated review with the given ID.
     */
    @PutMapping("/review/{id}")
    public ReviewResponseDto updateReview(@PathVariable int id, @RequestBody ReviewRequestDto review) {
        Review r = reviewService.updateReview(id, review.getRating(), review.getComment(), review.getReply());
        return new ReviewResponseDto(r);
    }

    /**
     * Delete thew person with the given ID.
     *
     * @param id the id of the review to delete.
     * @return void.
     */
    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}
