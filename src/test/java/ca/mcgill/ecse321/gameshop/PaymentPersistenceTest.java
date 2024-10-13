package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.model.Order;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentPersistenceTest {

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
    public void testPersistPayment() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();

            Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234");

            Customer customer = new Customer(person, "123 Main St", null, null);

            Guest guest = new Guest();

            Cart cart = new Cart(customer, guest);

            Payment payment = new Payment("Test address", 0000, 2024-10-12, 123, 10000, customer, 1111, "2024-10-11", 1234.0, cart);

            Order order = new Order(1111, "2024-09-12", 1234.0, payment, cart);

            Customer associatedCustomer = payment.getCustomer();
            Order associatedOrder = order.getOrder();

            // Persist the Cart object (which cascades and saves Customer and Guest)
            session.persist(payment);
            transaction.commit();

            // Read the Cart from the database
            Payment persistedPayment = session.get(Payment.class, payment);

            // Validate the persisted Cart and its associations
            assertNotNull(persistedPayment);
            assertNotNull(persistedPayment.getCustomer());
            assertNotNull(persistedPayment.getGames());
            assertEquals(associatedCustomer, persistedPayment.getCustomer());
            assertEquals(associatedOrder, persistedPayment.getOrder());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}