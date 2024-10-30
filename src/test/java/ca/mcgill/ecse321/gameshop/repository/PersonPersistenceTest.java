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

        Person person = new Person("joseph", "joseph@mail.mcgill.ca", "password123", "555-1234");
        person = personRepo.save(person);

        Person personFromDB = personRepo.findPersonByUserId(person.getUserId());

        assertNotNull(personFromDB);
        assertEquals(personFromDB.getEmail(),("joseph@mail.mcgill.ca"));
    }
}