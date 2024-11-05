package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Review;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    public Review findReviewByReviewId(int reviewId);
}
