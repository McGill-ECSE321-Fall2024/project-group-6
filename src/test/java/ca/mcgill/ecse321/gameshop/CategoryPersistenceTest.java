package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CategoryPersistenceTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        // Open a new session for each test (uses a test database)
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @AfterEach
    public void tearDown() {
        // Close the session after each test
        if (session != null) {
            session.close();
        }
    }

    @Test
    public void testPersistCategory() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();

            Category category = new Category("Test Category");

            String name = category.getCategoryName();
            List<Game> games = category.getGames();

            // Persist the Cart object (which cascades and saves Customer and Guest)
            session.persist(category);
            transaction.commit();

            // Read the Cart from the database
            Category persistedCategory = session.get(Category.class, category);

            // Validate the persisted Cart and its associations
            assertNotNull(persistedCategory);
            assertNotNull(persistedCategory.getGames());
            assertNotNull(persistedCategory.getCategoryName());
            assertEquals(games, persistedCategory.getGames());
            assertEquals(name, persistedCategory.getCategoryName());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
