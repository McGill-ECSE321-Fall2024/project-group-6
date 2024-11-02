package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create new customer
    @PostMapping
    public Customer createCustomer() {
        return customerService.createCustomer();
    }

    // Retrieve all customers from the DB
    @GetMapping
    public Iterable<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    // Find customer by their ID
    @GetMapping("/customer/{id}")
    public Customer findCustomerByID(@PathVariable int id) {
        return customerService.findCustomerByID(id);
    }

    // Update customer by their ID
    @PutMapping("/customer/{id}")
    public Customer updateCustomer(@PathVariable int id) {
        return customerService.updateCustomer(id);
    }

    // Delete customer by their ID
    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

    // Create review for a game
    @PostMapping("/customer/{customerId}/reviews")
    public Review createReview(
            @RequestParam int gameId,
            @RequestParam Review.StarRating rating,
            @RequestParam String comment,
            @RequestParam int amountOfLikes) {
        return customerService.createReview(gameId,rating,comment,amountOfLikes);
    }

}
