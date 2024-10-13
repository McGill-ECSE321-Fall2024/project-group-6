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
    public void testPersistEmployee() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();
            // Create a new Person for the customer and guest
            Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234");
            Employee employee = new Employee(person, "Test task");

            List<Game> games = employee.getCreated();
            int created = employee.numberOfCreated();

            // Persist the Cart object (which cascades and saves Customer and Guest)
            session.persist(employee);
            transaction.commit();

            // Read the Cart from the database
            Employee persistedEmployee = session.get(Employee.class, employee);

            // Validate the persisted Cart and its associations
            assertNotNull(persistedEmployee);
            assertNotNull(persistedEmployee.getCreated());
            assertEquals(games, persistedEmployee.getCreated());
            assertEquals(created, persistedEmployee.numberOfCreated());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
