package ca.mcgill.ecse321.gameshop.repository;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.model.Role;

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


        Person person = new Person("johndoe", "john@hotmail.com", "password123", "555-1234");
        person = personRepo.save(person);


        Customer customer = new Customer( person, "1234 Montreal");

        customer = roleRepo.save(customer);

        Role customerFromDB = roleRepo.findRoleByRoleId(customer.getRoleId());
        /**
         * Relational testing (Customer is a subclass compared to Role)
         */
        assertNotNull(customerFromDB);
        assertEquals(customerFromDB.getRoleId(),customer.getRoleId());
    }
}