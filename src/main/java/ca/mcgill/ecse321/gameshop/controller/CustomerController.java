package ca.mcgill.ecse321.gameshop.controller;


import ca.mcgill.ecse321.gameshop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;

import ca.mcgill.ecse321.gameshop.model.*;
import java.util.*;
import ca.mcgill.ecse321.gameshop.service.*;

@RestController
public class CustomerController {


    @Autowired
    private CustomerService customerService;



    @PostMapping("/customers")
    public CustomerResponseDto createEmployee(@Valid @RequestBody CustomerRequestDto customerToCreate) {
    Person person= new Person(customerToCreate.getUsername(),customerToCreate.getEmail(),customerToCreate.getPassword(),customerToCreate.getPhone());
        Customer customer = customerService.createCustomer(person,customerToCreate.getShippingAddress());

        return new CustomerResponseDto(customer);
    }


    @GetMapping("/customers/{cid}")
    public CustomerResponseDto findCustomerById(@PathVariable int cid) {
        Customer customer=customerService.getCustomerByID(cid);

        return new CustomerResponseDto(customer);
    }
    @GetMapping("/customers")
    public CustomerListDto getAllCustomers() {
        List<CustomerResponseDto> customers = new ArrayList<>();
        for (Customer c: customerService.getAllCustomers()) {
            customers.add(new CustomerResponseDto(c));
        }
        return new CustomerListDto(customers);
    }


    @DeleteMapping("/customers/{cid}")
    public void deleteCustomer(@PathVariable int cid) {
        customerService.deleteCustomer(cid);

    }


    @PutMapping("/customers/{cid}")
    public CustomerResponseDto updateCustomer(@PathVariable int cid, @RequestBody CustomerRequestDto customer) {
       Customer c = customerService.updateCustomer(cid, customer.getUsername(),customer.getEmail(),customer.getPhone(),customer.getPassword(),customer.getShippingAddress());

        return new CustomerResponseDto(c);
    }


}
