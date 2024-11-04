package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gameshop.dto.CustomerListDto;
import ca.mcgill.ecse321.gameshop.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CustomerResponseDto;

import ca.mcgill.ecse321.gameshop.dto.ReviewListDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gameshop.dto.ReviewResponseDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create new customer
    @PostMapping("/customer")
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customer) {
        Customer c = customerService.createCustomer();
        return new CustomerResponseDto(c);
    }

    // Retrieve all customers from the DB
    @GetMapping("/customer")
    public CustomerListDto getAllCustomers() {
        List<CustomerResponseDto> customers = new ArrayList<>();
        for (Customer c: customerService.getAllCustomers()) {
            customers.add(new CustomerResponseDto(c));
        }
        return new CustomerListDto(customers);
    }

    // Find customer by their ID
    @GetMapping("/customer/{id}")
    public CustomerResponseDto findCustomerByID(@PathVariable int id) {
        Customer c = customerService.findCustomerByID(id);
        return new CustomerResponseDto(c);
    }

    // Update customer by their ID
    @PutMapping("/customer/{id}")
    public CustomerResponseDto updateCustomer(@PathVariable int id, @RequestBody CustomerRequestDto customer) {
        Customer c = customerService.updateCustomer(id);
        return new CustomerResponseDto(c);
    }

    // Delete customer by their ID
    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable int id, @RequestBody CustomerRequestDto customer) {
        customerService.deleteCustomer(id);
    }


    // Create review for a game
    @PostMapping("/customer/{customerId}/reviews")
    public ReviewResponseDto createReview(
            @PathVariable int customerId,
            @RequestParam int gameId,
            @RequestParam Review.StarRating rating,
            @RequestParam String comment,
            @RequestParam int amountOfLikes) {

        Review r = customerService.createReview(gameId,rating,comment,amountOfLikes);
        return new ReviewResponseDto(r);
    }

}
