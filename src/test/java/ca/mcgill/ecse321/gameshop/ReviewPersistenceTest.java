package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.ecse321.gameshop.model.Review.*;

public class ReviewPersistenceTest {

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
    public void testPersistReview() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();

            Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234");

            Customer customer = new Customer(person, "123 Main St", null, null);

            Person person2 = new Person("janedoe", "jane.doe@email.com", "password456", "000-1234");

            Manager manager = new Manager(person2);

            Employee testCreator = new Employee(person, "Test task");
            Category testCategory = new Category("testCategory");
            Game game = new Game("Test Game", "Test Description", 29.99f, 10, null, 1, manager, testCreator, testCategory);

            Review review = Review(StarRating.OneStar, "Good", 1000, 0001, customer, manager, game);
            Customer connectedCustomer = review.getCustomer();
            String comment = review.getComment();


            session.persist(review);
            transaction.commit();

            Review persistedReview = session.get(Review.class, review);

            assertNotNull(persistedReview);
            assertNotNull(persistedReview.getCustomer());
            assertNotNull(persistedReview.getComment());
            assertEquals(connectedCustomer, persistedReview.getCustomer());
            assertEquals(comment, persistedReview.getComment());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}