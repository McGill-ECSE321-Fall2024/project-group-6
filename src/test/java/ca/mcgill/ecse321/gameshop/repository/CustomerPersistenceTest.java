package ca.mcgill.ecse321.gameshop.repository;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Person;

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

        Customer customer = new Customer( person, "1234 Montreal");
        customer = customerRepo.save(customer);
        Customer customerFromDb = customerRepo.findCustomerByRoleId(customer.getRoleId());
        assertEquals(customerFromDb.getShippingAddress(),"1234 Montreal");
    }
}