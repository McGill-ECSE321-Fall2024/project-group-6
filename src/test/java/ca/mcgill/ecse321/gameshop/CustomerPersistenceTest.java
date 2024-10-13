package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CustomerPersistenceTest {

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
    public void testPersistCustomer() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();
            Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234");

            Customer customer = new Customer(person, "123 Main St", null, null);

            String address = customer.getShippingAddress();

            Cart carts = customer.getCarts();

            List<Review> reviews = customer.getReviews();

            session.persist(customer);
            transaction.commit();

            Customer persistedCustomer = session.get(Customer.class, customer);

            assertNotNull(persistedCustomer);
            assertNotNull(persistedCustomer.getCarts());
            assertNotNull(persistedCustomer.getReviews());
            assertNotNull(persistedCustomer.getShippingAddress());
            assertEquals(carts, persistedCustomer.getCarts());
            assertEquals(reviews, persistedCustomer.getReviews());
            assertEquals(address, persistedCustomer.getShippingAddress());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
