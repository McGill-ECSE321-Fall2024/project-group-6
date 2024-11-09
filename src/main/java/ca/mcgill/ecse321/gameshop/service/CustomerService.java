package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
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

        if(c.getPerson().getPassword().length()<10){
            throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
        }
        if(c.getPerson().getEmail()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
        }

        if(c.getPerson().getPhone()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
        }
        if(c.getPerson().getUsername()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
        }
        if(c.getShippingAddress()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Shipping address can not be null"));
        }
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
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        return c;
    }

    // Update an existing customer by their ID
    @Transactional
    public Customer updateCustomer(int id, String aUsername, String aEmail, String aPassword, String aPhone, String aShippingAddress) {
        Customer c = customerRepo.findCustomerByRoleId(id);


        if (c == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

            if (aPassword.length() < 10 && !aPassword.equals("disabled")) {
                throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
            }
            if (aEmail == null) {
                throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
            }

            if (aPhone == null) {
                throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
            }
            if (aUsername == null) {
                throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
            }
            if (aShippingAddress == null) {
                throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Shipping address can not be null"));
            }

        c.getPerson().setPhone(aPhone);
        c.getPerson().setEmail(aEmail);
        c.getPerson().setUsername(aUsername);
        c.getPerson().setPhone(aPhone);

        c.setShippingAddress(aShippingAddress);

        return customerRepo.save(c);
    }

    // Delete a customer by their ID
    @Transactional
    public void deleteCustomer(int id) {
        Customer c = customerRepo.findCustomerByRoleId(id);

        if (c == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        customerRepo.delete(c);
    }
}
