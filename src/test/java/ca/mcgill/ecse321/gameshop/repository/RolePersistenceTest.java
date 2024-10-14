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
public class RolePersistenceTest {


    // private WishlistRepository wishRepo;


    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PersonRepository personRepo;
    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        roleRepo.deleteAll();
        personRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadRole() {
        Random rand = new Random();
        int n = rand.nextInt(1000);

        Person person = new Person("johndoe", "john@hotmail.com", "password123", "555-1234", n);
        person = personRepo.save(person);


        Customer customer = new Customer(n, person, "1234 Montreal");

        customer = roleRepo.save(customer);

        Role customerFromDB = roleRepo.findRoleByRoleId(n);
        /**
         * Relational testing (Customer is a subclass compared to Role)
         */
        assertNotNull(customerFromDB);
        assertEquals(customerFromDB.getRoleId(),n);
    }
}