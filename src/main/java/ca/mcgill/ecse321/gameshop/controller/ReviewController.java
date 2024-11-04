package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Return all reviews in the DB.
     *
     * @return Return all reviews.
     */
    @GetMapping //IS THIS THE RIGHT RETURN TYPE
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }


    /**
     * Return the review with the given ID.
     *
     * @param id The primary key of the person to find.
     * @return The review with the given ID.
     */
    @GetMapping("/{id}") //handles HTTP get requests to the review with a specific ID
    public ReviewResponseDto getReviewById(@PathVariable int id) {
        Review review = reviewService.getReviewById(id);
        return new ReviewResponseDto(review);
    }

    /**
     * Return the new review
     *
     * @param review The review object.
     * @return The new review object.
     */
    @PostMapping("/addReview")
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto review) {
        //Logic to add the review to the DB
        Review createdReview = new Review(review.getRating(), review.getComment(), review.getAmountOfLikes());
        return new ReviewResponseDto(createdReview); //no need to create a response entity because command will al
    }

    /**
     * Return the updated review with the given ID.
     *
     * @param id the ID of the review
     * @return The updated review with the given ID.
     */
    @PutMapping("/{id}")
    public ReviewResponseDto updateReview(@PathVariable int id, @RequestBody Review reviewDetails) {
        Review updatedReview = reviewService.updateReview(id, reviewDetails);
        return new ReviewResponseDto(updatedReview);
    }

    /**
     * Return the response DTO of the review deletion.
     *
     * @param id the id of the review to delete.
     * @return The response DTO of the review deletion.
     */
    @DeleteMapping("/{id}") //IS THIS THE RIGHT RETURN TYPE
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}
