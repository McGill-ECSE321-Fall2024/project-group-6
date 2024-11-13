package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    public Review findReviewByReviewId(int reviewId);
}
