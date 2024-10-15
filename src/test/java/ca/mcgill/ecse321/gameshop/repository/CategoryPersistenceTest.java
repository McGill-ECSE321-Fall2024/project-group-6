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
public class CategoryPersistenceTest {


    // private WishlistRepository wishRepo;
    @Autowired
    private CategoryRepository categoryRepo;


    @BeforeEach

    @AfterEach
    public void clearDatabase() {
        categoryRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadCategory() {
        Random rand = new Random();
        int n = rand.nextInt(1000);
        Category category=new Category("Sports Games",n);

        Category categoryFromDb = categoryRepo.findCategoryByCategoryId(n);
        categoryFromDb = categoryRepo.save(category);
        assertNotNull(categoryFromDb);
        assertEquals(categoryFromDb.getCategoryName(),"Sports Games");

    }
}