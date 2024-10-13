package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CartPersistenceTest {

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
    public void testPersistCart() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();
            // Create a new Person for the customer and guest
            Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234");
            Guest guest = new Guest();

            Customer customer = new Customer(person, "123 Main St", null, null);

            Cart cart = new Cart(customer, guest);

            int games = cart.numberOfGames();

            // Persist the Cart object (which cascades and saves Customer and Guest)
            session.persist(cart);
            transaction.commit();

            // Read the Cart from the database
            Cart persistedCart = session.get(Cart.class, cart);

            // Validate the persisted Cart and its associations
            assertNotNull(persistedCart);
            assertNotNull(persistedCart.getCustomer());
            assertNotNull(persistedCart.getGuest());
            assertNotNull(persistedCart.numberOfGames());
            assertEquals("John Doe", persistedCart.getCustomer());
            assertEquals("Guest User", persistedCart.getGuest());
            assertEquals(games, persistedCart.numberOfGames());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
