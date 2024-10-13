package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ManagerPersistenceTest {

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
    public void testPersistManager() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();

            Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234");
            Manager manager = new Manager(person);
            int reviews = manager.numberOfReviews();
            List<Review> connectedReviews = manager.getReviews();

            // Persist the Cart object (which cascades and saves Customer and Guest)
            session.persist(manager);
            transaction.commit();

            // Read the Cart from the database
            Manager persistedManager = session.get(Manager.class, manager);

            // Validate the persisted Cart and its associations
            assertNotNull(persistedManager);
            assertNotNull(persistedManager.getReviews());
            assertNotNull(persistedManager.getGames());
            assertEquals(connectedReviews, persistedManager.getReviews());
            assertEquals(reviews, persistedManager.numberOfReviews());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}