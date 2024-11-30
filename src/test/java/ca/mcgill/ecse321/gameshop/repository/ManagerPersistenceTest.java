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
public class ManagerPersistenceTest {


    // private WishlistRepository wishRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private PersonRepository personRepo;

    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        managerRepo.deleteAll();
        personRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadManager() {

        Person person = new Person("joseph", "joe.roustom@mcgill.ca", "password123", "555-1234");
        person = personRepo.save(person);

        Manager manager = new Manager(person);
        manager = managerRepo.save(manager);
        Manager managerFromDB = managerRepo.findManagerByRoleId(manager.getRoleId());
        assertNotNull(managerFromDB);
        assertEquals(managerFromDB.getPerson().getPassword(),"password123");
    }
}