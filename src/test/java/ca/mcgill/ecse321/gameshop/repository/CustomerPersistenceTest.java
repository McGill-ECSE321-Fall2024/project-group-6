package ca.mcgill.ecse321.gameshop.repository;

import ca.mcgill.ecse321.gameshop.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class CustomerPersistenceTest {


    // private WishlistRepository wishRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PersonRepository personRepo;

    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        customerRepo.deleteAll();
        personRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadCustomer() {

        Person person = new Person("johndoe", "john.doe@gmail.com", "password123", "555-1234");
        person = personRepo.save(person);


        Customer customer = new Customer( person, "1234 Montreal");
        customer = customerRepo.save(customer);
        Customer customerFromDb = customerRepo.findCustomerByRoleId(customer.getRoleId());
        assertEquals(customerFromDb.getShippingAddress(),"1234 Montreal");
    }
}