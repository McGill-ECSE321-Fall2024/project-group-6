package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistPersistenceTest {

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
    public void testPersistRole() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();

            Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234");

            Customer customer = new Customer(person, "123 Main St", null, null);

            Wishlist wishlist = new Wishlist(customer);

            int numberOfGames = wishlist.numberOfGames();
            Customer associatedCustomer = wishlist.getCustomer();


            session.persist(wishlist);
            transaction.commit();

            Wishlist persistedWishlist = session.get(Wishlist.class, wishlist);

            assertNotNull(persistedWishlist);
            assertNotNull(persistedWishlist.getCustomer());
            assertNotNull(persistedWishlist.numberOfGames());
            assertEquals(associatedCustomer, persistedWishlist.getCustomer());
            assertEquals(numberOfGames, persistedWishlist.numberOfGames());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}