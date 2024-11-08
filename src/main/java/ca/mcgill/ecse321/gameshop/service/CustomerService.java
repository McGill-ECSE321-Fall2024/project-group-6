package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ReviewRepository reviewRepo;

    // Create new customer
    @Transactional
    public Customer createCustomer(Person person, String shippingAddress) {
        Customer customer = new Customer();
        return customerRepo.save(customer);
    }

    // Retrieve all customers from repository
    @Transactional
    public Iterable<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Find customer by their ID
    @Transactional
    public Customer findCustomerByID(int id) {
        Customer customer = customerRepo.findCustomerByRoleId(id);

        // Exception if no customer is found
        if (customer == null) {
            throw new RuntimeException("Customer with the following ID does not exist:" + id);
        }
        return customer;
    }

    // Update an existing customer by their ID
    @Transactional
    public Customer updateCustomer(int id, Customer customerDetails) {
        Customer customer = customerRepo.findCustomerByRoleId(id);
        // Exception if no customer is found
        if (customer == null) {
            throw new RuntimeException("Customer with the following ID does not exist:" + id);
        }
        customer.setPerson(customerDetails.getPerson());
        customer.setShippingAddress(customerDetails.getShippingAddress());
        return customerRepo.save(customer);
    }

    // Delete a customer by their ID
    @Transactional
    public void deleteCustomer(int id) {
        Customer customer = customerRepo.findCustomerByRoleId(id);

        // Exception if no customer is found
        if (customer == null) {
            throw new RuntimeException("Customer with the following ID does not exist:" + id);
        }
        customerRepo.delete(customer);
    }


    // Potential duplicate with ReviewService
    // Create a review for a game
    @Transactional
    public Customer createReview(int reviewID,Customer aCustomer) {
        Review review = reviewRepo.findReviewByReviewId(reviewID);
        aCustomer.addReview(review);
        return customerRepo.save(aCustomer);
    }

}
