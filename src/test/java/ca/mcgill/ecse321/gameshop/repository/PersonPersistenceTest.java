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
public class PersonPersistenceTest {


    // private WishlistRepository wishRepo;



    @Autowired
    private PersonRepository personRepo;
    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        personRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadEmployee() {
        Random rand = new Random();
        int n = rand.nextInt(1000);
        Person person = new Person("joseph", "joseph@mail.mcgill.ca", "password123", "555-1234", n);
        person = personRepo.save(person);

        Person personFromDB = personRepo.findPersonByUserId(n);

        assertNotNull(personFromDB);
        assertEquals(personFromDB.getEmail(),("joseph@mail.mcgill.ca"));
    }
}