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

        Category category=new Category("Sports Games");
        category = categoryRepo.save(category);

        Category categoryFromDb = categoryRepo.findCategoryByCategoryId(category.getCategoryId());

        assertNotNull(categoryFromDb);
        assertEquals(categoryFromDb.getCategoryName(),"Sports Games");

    }
}