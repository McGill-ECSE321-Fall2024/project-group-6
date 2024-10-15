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
        Random rand = new Random();
        int n = rand.nextInt(1000);
        Person person = new Person("johndoe", "john.doe@gmail.com", "password123", "555-1234", n);
        person = personRepo.save(person);


        Customer customer = new Customer(n, person, "1234 Montreal");
        customer = customerRepo.save(customer);
        Customer customerFromDb = customerRepo.findCustomerByRoleId(n);
        assertEquals(customerFromDb.getShippingAddress(),"1234 Montreal");
    }
}