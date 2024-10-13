package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class EmployeePersistenceTest {

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
    public void testPersistGuest() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();

            Guest guest = new Guest();

            int carts = guest.numberOfGuestCart();
            List<Cart> allCarts = guest.getGuestCart();

            // Persist the Cart object (which cascades and saves Customer and Guest)
            session.persist(guest);
            transaction.commit();

            // Read the Cart from the database
            Guest persistedGuest = session.get(Guest.class, guest);

            // Validate the persisted Cart and its associations
            assertNotNull(persistedGuest);
            assertNotNull(persistedGuest.getGuestCart());
            assertEquals(allCarts, persistedGuest.getGuestCart());
            assertEquals(carts, persistedGuest.numberOfGuestCart());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}