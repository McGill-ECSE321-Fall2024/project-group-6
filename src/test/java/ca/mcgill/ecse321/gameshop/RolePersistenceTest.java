package ca.mcgill.ecse321.gameshop;

import ca.mcgill.ecse321.gameshop.model.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class RolePersistenceTest {

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
            Person person2 = new Person("janedoe", "jane.doe@email.com", "password456", "000-1234");

            Role role = new Role(person);
            Person connectedPerson = role.getPerson();
            boolean isPersonSet = role.setPerson(person2);


            session.persist(role);
            transaction.commit();

            Role persistedRole = session.get(Role.class, role);

            assertNotNull(persistedRole);
            assertNotNull(persistedRole.getPerson());
            assertNotNull(persistedRole.setPerson(person2));
            assertEquals(connectedPerson, persistedRole.getPerson());
            assertEquals(isPersonSet, persistedRole.setPerson(person2));

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}