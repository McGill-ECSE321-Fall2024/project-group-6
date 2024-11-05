package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    // Inject CustomerRepository to handle database operations
    @Autowired
    private CustomerRepository customerRepo;

    // Create new customer and save it in the repository
    @Transactional
    public Customer createCustomer(Person aPerson, String aShippingAddress) {
        Customer c = new Customer(aPerson, aShippingAddress);
        return customerRepo.save(c);
    }

    // Retrieve all customers from repository
    public Iterable<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Find a customer by their ID
    @Transactional
    public Customer getCustomerByID(int id) {
        Customer c = customerRepo.findCustomerByRoleId(id);

        // Exception if no customer is found
        if (c == null) {
            throw new RuntimeException("Customer with ID " + id + "does not exist.");
        }

        return c;
    }

    // Update an existing customer by their ID
    @Transactional
    public Customer updateCustomer(int id, Person aPerson, String aShippingAddress) {
        Customer c = customerRepo.findCustomerByRoleId(id);

        if (c == null) {
            throw new RuntimeException("Customer with ID " + id + "does not exist.");
        }

        c.setPerson(aPerson);
        c.setShippingAddress(aShippingAddress);

        return customerRepo.save(c);
    }

    // Delete a customer by their ID
    @Transactional
    public void deleteCustomer(int id) {
        Customer c = customerRepo.findCustomerByRoleId(id);

        if (c == null) {
            throw new RuntimeException("Customer with ID " + id + "does not exist.");
        }
        
        customerRepo.delete(c);
    }
}
