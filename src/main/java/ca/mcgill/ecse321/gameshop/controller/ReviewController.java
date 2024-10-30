package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping
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
    public ResponseEntity<Review> getReviewById(@PathVariable int id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok) //sent to the response body
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Return the new review
     *
     * @param review The review object.
     * @return The new review object.
     */
    @PostMapping("/addReview")
    public Review createReview(@RequestBody Review review) {
        //Logic to add the review to the DB
        return reviewService.createReview(review); //no need to create a response entity because command will al
    }

    /**
     * Return the updated review with the given ID.
     *
     * @param id the ID of the review
     * @return The updated review with the given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable int id, @RequestBody Review reviewDetails) {
        try {
            Review updatedReview = reviewService.updateReview(id, reviewDetails);
            return ResponseEntity.ok(updatedReview);
        } catch (RuntimeException e) { //if this review id does not exist
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Return the response entity of the review like.
     *
     * @param id the id of the review to like.
     * @return The response entity of the review like.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReviewLikes(@PathVariable int id){
        try {
            Review likedReview = reviewService.likeReview(id);
            return ResponseEntity.ok(likedReview);
        } catch (RuntimeException e) { //if this review id does not exist
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Return the response entity of the review deletion.
     *
     * @param id the id of the review to delete.
     * @return The response entity of the review deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
