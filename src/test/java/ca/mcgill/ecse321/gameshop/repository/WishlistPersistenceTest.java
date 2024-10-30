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
public class WishlistPersistenceTest {


    // private WishlistRepository wishRepo;


    @Autowired
    private WishlistRepository wishlistRepo;

    @BeforeEach

    @AfterEach
    public void clearDatabase() {
        wishlistRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadWishlist() {




        Wishlist wishlist= new Wishlist();
        wishlist=wishlistRepo.save(wishlist);
        Wishlist wishlistFromDb= wishlistRepo.findWishlistByWishlistId(wishlist.getWishlistId());
        assertNotNull(wishlistFromDb);


    }
  
}