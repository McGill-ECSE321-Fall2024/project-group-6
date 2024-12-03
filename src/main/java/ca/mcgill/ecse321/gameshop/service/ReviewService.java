package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    // Inject ReviewRepository to handle database operations
    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private GameRepository gameRepo;

    /**
     * Creates a new review and saves it in the repository.
     *
     * @author Annabelle Huynh-Rondeau
     * @param aRating The rating given to the review (must be non-null).
     * @param aComment The comment text for the review.
     * @return The newly created Review object.
     * @throws GameShopException If the rating is null.
     */
    @Transactional
    public Review createReview(Review.StarRating aRating, String aComment, Customer aCustomer, Game aGame) {
        // Check if rating is null
        if (aRating == null) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Rating cannot be empty");
        }
        if(aCustomer==null){
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Customer field is null");
        }
        Customer customerFromDb= customerRepo.findCustomerByRoleId(aCustomer.getRoleId());
        if(customerFromDb==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, "Customer associated with this review does not exist");
        }
        if(aGame==null){
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Game field is null");
        }
        Game gameFromDb= gameRepo.findGameByGameId(aGame.getGameId());

        if(gameFromDb==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, "Game associated with this review does not exist");
        }
        Review r = new Review(aRating, aComment, 0, aCustomer, aGame);
        // Set an empty reply if it's not initialized
        r.setReply(r.getReply() != null ? r.getReply() : "");

        return reviewRepo.save(r);
    }

    /**
     * Retrieves all reviews from the repository.
     *
     * @author Annabelle Huynh-Rondeau
     * @return An iterable collection of all Review objects.
     */
    public Iterable<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    /**
     * Finds a review by its ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @param id The ID of the review to retrieve.
     * @return The Review object corresponding to the given ID.
     * @throws GameShopException If no review with the given ID is found.
     */
    public Review getReviewById(int id) {
        Review r = reviewRepo.findReviewByReviewId(id);

        // Throw an exception if no review is found
        if (r == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Review with ID " + id + " does not exist."));
        }

        return r;
    }

    /**
     * Updates an existing review by ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @param id The ID of the review to update.
     * @param aRating The updated rating.
     * @param aComment The updated comment.
     * @param aReply The updated reply to the review.
     * @return The updated Review object.
     * @throws GameShopException If no review with the given ID is found or rating is null.
     */
    @Transactional
    public Review updateReview(int id, Review.StarRating aRating, String aComment, String aReply) {
        Review r = reviewRepo.findReviewByReviewId(id);

        if (r == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Review with ID " + id + " does not exist."));
        }

        // Check if rating is null
        if (aRating == null) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Rating cannot be empty");
        }

        r.setRating(aRating);
        r.setComment(aComment);
        r.setReply(aReply);
        // The number of likes is not updated via this method as it's managed separately

        return reviewRepo.save(r);
    }

    /**
     * Deletes a review by its ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @param id The ID of the review to delete.
     * @throws GameShopException If no review with the given ID is found.
     */
    @Transactional
    public void deleteReview(int id) {
        Review r = reviewRepo.findReviewByReviewId(id);

        if (r == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Review with ID " + id + " does not exist."));
        }

        reviewRepo.delete(r);
    }

    /**
     * Increments the number of likes on a review by 1.
     *
     * @author Annabelle Huynh-Rondeau
     * @param id The ID of the review to like.
     * @return The updated Review object with incremented likes.
     * @throws RuntimeException If the review with the given ID is not found.
     */
    public Review likeReview(int id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setAmountOfLikes(review.getAmountOfLikes() + 1);

        return reviewRepo.save(review);
    }

    /**
     * Allows a user to reply to a review.
     *
     * @author Annabelle Huynh-Rondeau
     * @param id The ID of the review to reply to.
     * @param aRating The rating given by the user in the reply.
     * @param aComment The comment text in the reply.
     * @param aAmountOfLikes The amount of likes for the reply (not used in this method).
     * @param reply The text of the reply to the review.
     * @return The updated Review object with the reply.
     * @throws GameShopException If the reply is null or empty.
     */
    public Review replyToReview(int id, Review.StarRating aRating, String aComment, int aAmountOfLikes, String reply) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Check if the reply is null or empty
        if (reply == null) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Reply cannot be empty");
        }

        review.setReply(reply);

        return reviewRepo.save(review);
    }
}