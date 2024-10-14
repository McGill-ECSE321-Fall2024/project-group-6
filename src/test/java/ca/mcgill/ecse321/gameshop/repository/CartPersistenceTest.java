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
public class CartPersistenceTest {


    // private WishlistRepository wishRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PersonRepository personRepo;

    @BeforeEach

    @AfterEach
    public void clearDatabase() {
        cartRepo.deleteAll();
       customerRepo.deleteAll();
       personRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadCart() {
        Random rand = new Random();
        int n = rand.nextInt(1000);
        Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234", n);
        person = personRepo.save(person);


        Customer customer = new Customer(n, person, "1234 Montreal");
        customer = customerRepo.save(customer);
        Cart cart = new Cart(n, customer);
        cart = cartRepo.save(cart);
        Cart cartFromDb = cartRepo.findCartByCartId(n);
        //Customer customerFromDb=customerRepo.findCustomerById(0);
        assertNotNull(cartFromDb);
        assertEquals(cartFromDb.getCartId(),n);
        //assertNotNull(customerFromDb);

    }
    }