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
    public void testPersistGame() {
        Transaction transaction = null;
        try {
            // Start a transaction
            transaction = session.beginTransaction();

            Person person = new Person("johndoe", "john.doe@email.com", "password123", "555-1234");
            Manager testManager = new Manager(person);
            Employee testCreator = new Employee(person, "Test task");
            Category testCategory = new Category("testCategory");
            Game game = new Game("Test Game", "Test Description", 29.99f, 10, null, 1, testManager, testCreator, testCategory);

            int gameId = game.getGameId();

            // Persist the Cart object (which cascades and saves Customer and Guest)
            session.persist(game);
            transaction.commit();

            // Read the Cart from the database
            Game persistedGame = session.get(Game.class, game);

            // Validate the persisted Cart and its associations
            assertNotNull(persistedGame);
            assertNotNull(persistedGame.getManager());
            assertNotNull(persistedGame.getCreator());
            assertEquals(testManager, persistedGame.getManager());
            assertEquals(testCreator, persistedGame.getCreator());
            assertEquals(gameId, persistedGame.getGameId());

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}