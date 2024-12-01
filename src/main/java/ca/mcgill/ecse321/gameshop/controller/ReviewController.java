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

import ca.mcgill.ecse321.gameshop.dto.ReviewListDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private GameRepository gameRepo;

    /**
     * Create the new review
     * @author Annabelle Huynh-Rondeau
     * @param review The review to create.
     * @return The new review.
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PostMapping("/review/{cId}/{gId}")
    public ReviewResponseDto createReview(@PathVariable int cId, @PathVariable int gId, @RequestBody ReviewRequestDto review) {
        Customer customer = customerRepo.findCustomerByRoleId(cId);
        Game game = gameRepo.findGameByGameId(gId);
        Review r = reviewService.createReview(review.getRating(), review.getComment(), customer, game);
        return new ReviewResponseDto(r);
    }

    /**
     * Return all reviews.
     * @author Annabelle Huynh-Rondeau
     * @return All the reviews.
     */
    @CrossOrigin(origins = "http://localhost:8087")
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
     * @author Annabelle Huynh-Rondeau
     * @param id The primary key of the person to find.
     * @return The review with the given ID.
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/review/{id}")
    public ReviewResponseDto getReviewById(@PathVariable int id) {
        Review r = reviewService.getReviewById(id);
        return new ReviewResponseDto(r);
    }

    /**
     * Update the review with the given ID.
     * @author Annabelle Huynh-Rondeau
     * @param id the ID of the review
     * @return The updated review with the given ID.
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/review/{id}")
    public ReviewResponseDto updateReview(@PathVariable int id, @RequestBody ReviewRequestDto review) {
        Review r = reviewService.updateReview(id, review.getRating(), review.getComment(), review.getReply());
        return new ReviewResponseDto(r);
    }

    /**
     * Delete thew person with the given ID.
     * @author Annabelle Huynh-Rondeau
     * @param id the id of the review to delete.
     * @return void.
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}