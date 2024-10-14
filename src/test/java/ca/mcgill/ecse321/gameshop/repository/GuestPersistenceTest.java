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
public class GuestPersistenceTest {


    // private WishlistRepository wishRepo;

    @Autowired
    private GuestRepository guestRepo;


    @BeforeEach

    @AfterEach
    public void clearDatabase() {

       guestRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadGuest() {
        Random rand = new Random();
        int n = rand.nextInt(1000);
        Guest guest = new Guest(n);
        guest = guestRepo.save(guest);
        Guest guestFromDB = guestRepo.findGuestByGuestId(n);
        assertNotNull(guestFromDB);

    }
}