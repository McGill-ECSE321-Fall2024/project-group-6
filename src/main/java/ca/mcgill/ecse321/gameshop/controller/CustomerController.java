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

import ca.mcgill.ecse321.gameshop.dto.CustomerListDto;
import ca.mcgill.ecse321.gameshop.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.service.CustomerService;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Create a new customer.
     *
     * @param customer The customer to create.
     * @return The created customer, including their ID.
     */
    @PostMapping("/customer")
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customer) {
        Person p = new Person(customer.getUsername(), customer.getEmail(), customer.getPassword(), customer.getPhone());
        Customer c = customerService.createCustomer(p, customer.getShippingAddress());
        return new CustomerResponseDto(c);
    }

    /**
     * Return all customers.
     *
     * @return All the customers.
     */
    @GetMapping("/customer")
    public CustomerListDto getAllCustomers() {
        List<CustomerResponseDto> customers = new ArrayList<>();

        for (Customer c: customerService.getAllCustomers()) {
            customers.add(new CustomerResponseDto(c));
        }
        
        return new CustomerListDto(customers);
    }

    /**
     * Return the customer with the given ID.
     *
     * @param id The primary key of the customer to find.
     * @return The customer with the given ID.
     */
    @GetMapping("/customer/{id}")
    public CustomerResponseDto getCustomerByID(@PathVariable int id) {
        Customer c = customerService.getCustomerByID(id);
        return new CustomerResponseDto(c);
    }

    /**
     * Update the customer with the given ID.
     *
     * @param id The primary key of the customer to find.
     * @return The updated customer with the given ID.
     */
    @PutMapping("/customer/{id}")
    public CustomerResponseDto updateCustomer(@PathVariable int id, @RequestBody CustomerRequestDto customer) {
        Person p = new Person(customer.getUsername(), customer.getEmail(), customer.getPhone(), customer.getPassword());
        Customer c = customerService.updateCustomer(id, p, customer.getShippingAddress());
        return new CustomerResponseDto(c);
    }

    /**
     * Delete the customer with the given ID.
     *
     * @param id The primary key of the customer to find.
     * @return void.
     */
    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }
}
