package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.model.Person;
import jakarta.transaction.Transactional;


public class CustomerService {

    @Autowired
    private CustomerRepository customerrepo;
    @Autowired
    private CategoryRepository catrepo;
    @Autowired
    private GameRepository gamerepo;
    @Autowired
    private ReviewRepository reviewrepo;

    // Create new customer
    @Transactional
    public Customer createCustomer() {
        Customer customer = new Customer();
        return customerrepo.save(customer);
    }

    // Retrieve all customers from repository
    @Transactional
    public Iterable<Customer> getAllCustomers() {
        return customerrepo.findAll();
    }

    // Find customer by their ID
    @Transactional
    public Customer findCustomerByID(int id) {
        Customer customer = customerrepo.findCustomerByRoleId(id);

        // Exception if no customer is found
        if (customer == null) {
            throw new RuntimeException("Customer with the following ID does not exist:" + id);
        }
        return customer;
    }

    // Update an existing customer by their ID
    @Transactional
    public Customer updateCustomer(int id) {
        Customer customer = customerrepo.findCustomerByRoleId(id);

        // Exception if no customer is found
        if (customer == null) {
            throw new RuntimeException("Customer with the following ID does not exist:" + id);
        }
        return customerrepo.save(customer);
    }

    // Delete a customer by their ID
    @Transactional
    public void deleteCustomer(int id) {
        Customer customer = customerrepo.findCustomerByRoleId(id);

        // Exception if no customer is found
        if (customer == null) {
            throw new RuntimeException("Customer with the following ID does not exist:" + id);
        }
        customerrepo.delete(customer);
    }

    //TODO associate review to a game in particular
    // Create a review for a game
    @Transactional
    public Review createReview(int gameId, Review.StarRating aRating, String aComment, int aAmountOfLikes) {
        Game game = gamerepo.findGameByGameId(gameId);

        Review review = new Review(aRating, aComment, 0);
        return reviewrepo.save(review);
    }

}
