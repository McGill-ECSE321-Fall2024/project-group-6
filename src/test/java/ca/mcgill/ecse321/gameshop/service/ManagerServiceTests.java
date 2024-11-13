package ca.mcgill.ecse321.gameshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gameshop.exception.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.*;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ManagerServiceTests {
    @Mock
    private ManagerRepository mockRepo;
    @Mock
    private PersonRepository repo;
    @InjectMocks
    private ManagerService service;

    private static final String VALID_NAME = "Will";
    private static final String VALID_EMAIL = "will@aol.com";
    private static final String VALID_PASSWORD = "NotTimHeres";
    private static final String VALID_PHONE ="123456";
    private static final int ID= 10;

    /**
     * test valid creation of manager
     */
    @Test
    public void testCreateValidManager() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        when(mockRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(repo.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Person person= new Person(VALID_NAME, VALID_EMAIL,VALID_PASSWORD, VALID_PHONE);
        Manager createdManager = service.createManager(person);

        // Assert
        assertNotNull(createdManager);
        assertEquals(VALID_NAME, createdManager.getPerson().getUsername());
        assertEquals(VALID_EMAIL, createdManager.getPerson().getEmail());
        assertEquals(VALID_PASSWORD, createdManager.getPerson().getPassword());
        assertEquals(VALID_PHONE, createdManager.getPerson().getPhone());
        verify(mockRepo, times(1)).save(createdManager);
    }
    /**
     * test invalid creation of more than one manager (only one manager can exist)
     */
    @Test
    public void testCreateMoreThanOneManager() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        when(mockRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(repo.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        // Act
        String email = "joker@aol.com";
        Person person= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);
        Manager createdManager = service.createManager(person);
        String email2 = "joke@aol.com";
        Person person2= new Person(VALID_NAME, email2,VALID_PASSWORD, VALID_PHONE);
        // Assert
        List<Manager> managers= new ArrayList<>();
        managers.add(createdManager);

        assertNotNull(createdManager);
        assertEquals(VALID_NAME, createdManager.getPerson().getUsername());
        assertEquals(email, createdManager.getPerson().getEmail());
        assertEquals(VALID_PASSWORD, createdManager.getPerson().getPassword());
        assertEquals(VALID_PHONE, createdManager.getPerson().getPhone());

        when(mockRepo.findAll()).thenReturn(managers);

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createManager(person2));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
        assertEquals("Manager already exists", ex.getMessage());
    }
    /**
     * test creation of manager with invalid phone number
     */
    @Test
    public void testCreateManagerWithInvalidPhone() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
       // when(mockRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        String email="joseph.roustom@gmail.com";
        Person p= new Person(VALID_NAME, email,VALID_PASSWORD, null);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createManager(p));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Phone number can not be null", ex.getMessage());
    }
    /**
     * test creation of manager with invalid username
     */
    @Test
    public void testCreateManagerWithInvalidUsername() {

        String email="josep.roustom@gmail.com";
        Person p= new Person(null, email,VALID_PASSWORD, VALID_PHONE);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createManager(p));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Username can not be null", ex.getMessage());
    }
    /**
     * test creation of manager with invalid email
     */
    @Test
    public void testCreateManagerWithInvalidEmail() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        Person p= new Person(VALID_NAME, null,VALID_PASSWORD, VALID_PHONE);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createManager(p));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Email can not be null", ex.getMessage());
    }
    /**
     * test creation of manager with invalid password length
     */
    @Test
    public void testCreateManagerWithInvalidPassword() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p

        String email="jose.roustom@gmail.com";
        Person p= new Person(VALID_NAME, email,"123", VALID_PHONE);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createManager(p));
        assertEquals(HttpStatus.LENGTH_REQUIRED, ex.getStatus());
        assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
    }

    /**
     * test get manager with valid id
     */

    @Test
    public void testGetManagerByValidId() {
        // Arrange
        String email="jos.roustom@gmail.com";
        Person person= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);
        when(mockRepo.findManagerByRoleId(ID)).thenReturn(new Manager(person));

        // Act
        Manager manager= mockRepo.findManagerByRoleId(ID);

        // Assert
        assertNotNull(manager);
        assertEquals(VALID_NAME, manager.getPerson().getUsername());
        assertEquals(email, manager.getPerson().getEmail());
        assertEquals(VALID_PASSWORD,manager.getPerson().getPassword());
        assertEquals(VALID_PHONE, manager.getPerson().getPhone());
    }
    /**
     * test get manager with invalid id
     */
    @Test
    public void testGetManagerByInvalidId() {
        // Arrange
        // Act
        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.findManagerById(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Manager with ID " + ID + " does not exist.", ex.getMessage());
    }
    /**
     * test update manager with valid id and info
     */
    @Test
    public void testUpdateManagerByValidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedbob@mail.mcgill.ca";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654321";

        String email="jo.roustom@gmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Manager existingManager=new Manager(aPerson);

        when(mockRepo.findManagerByRoleId(ID)).thenReturn(existingManager);
        // Mock the save method to return the updated person when save() is called
        when(mockRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock iom) -> {
            Manager updatedManager = iom.getArgument(0);
            updatedManager.getPerson().setUsername(updatedName);
            updatedManager.getPerson().setEmail(updatedEmail);
            updatedManager.getPerson().setPassword(updatedPassword);
            updatedManager.getPerson().setPhone(updatedPhone);
            return updatedManager;
        });

        // Act
        Manager updatedManager = service.updateManager(ID, updatedName, updatedEmail, updatedPassword, updatedPhone);

        // Assert
        assertNotNull(updatedManager);
        assertEquals(updatedName, updatedManager.getPerson().getUsername());
        assertEquals(updatedEmail, updatedManager.getPerson().getEmail());
        assertEquals(updatedPassword, updatedManager.getPerson().getPassword());
        assertEquals(updatedPhone, updatedManager.getPerson().getPhone());

        verify(mockRepo, times(1)).save(updatedManager);
    }
    /**
     * test update manager with invalid password length
     */

    @Test
    public void testUpdateManagerByInvalidPasswordLength() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedbo@mail.mcgill.ca";
        String updatedPassword = "newpas";
        String updatedPhone = "+1(514)7654321";

        String email="j.roustom@gmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Manager existingManager=new Manager(aPerson);

        when(mockRepo.findManagerByRoleId(ID)).thenReturn(existingManager);



        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateManager(ID, updatedName, updatedEmail, updatedPassword, updatedPhone));
        assertEquals(HttpStatus.LENGTH_REQUIRED, ex.getStatus());
        assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
    }
    /**
     * test update manager with invalid username
     */
    @Test
    public void testUpdateManagerByNullUsername() {
        // Arrange
        String updatedName = null;
        String updatedEmail = "updatedb@mail.mcgill.ca";
        String updatedPassword = "1111111111111";
        String updatedPhone = "+1(514)7654321";

        String email="roustom@gmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Manager existingManager=new Manager(aPerson);

        when(mockRepo.findManagerByRoleId(ID)).thenReturn(existingManager);



        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateManager(ID, updatedName, updatedEmail, updatedPassword, updatedPhone));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Username can not be null", ex.getMessage());
    }
    /**
     * test update manager with invalid phone number
     */
    @Test
    public void testUpdateManagerByNullPhone() {
        // Arrange
        String updatedName = "UpdateBob";
        String updatedEmail = "updated@mail.mcgill.ca";
        String updatedPassword = "1111111111111111";
        String updatedPhone = null;
        String email="oustom@gmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Manager existingManager=new Manager(aPerson);

        when(mockRepo.findManagerByRoleId(ID)).thenReturn(existingManager);


        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateManager(ID, updatedName, updatedEmail, updatedPassword, updatedPhone));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Phone number can not be null", ex.getMessage());
    }
    /**
     * test update manager with invalid email
     */
    @Test
    public void testUpdateManagerByNullEmail() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = null;
        String updatedPassword = "1111111111111111";
        String updatedPhone = "+1(514)7654321";
        String email="ustom@gmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Manager existingManager=new Manager(aPerson);

        when(mockRepo.findManagerByRoleId(ID)).thenReturn(existingManager);



        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateManager(ID, updatedName, updatedEmail, updatedPassword, updatedPhone));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Email can not be null", ex.getMessage());
    }
    /**
     * test update manager with invalid id
     */
    @Test
    public void testUpdateManagerByInvalidId() {
        // Arrange
        // Act
        // Assert
        String email="stom@gmail.com";
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateManager(ID, VALID_NAME, email, VALID_PASSWORD, VALID_PHONE));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Manager with ID " + ID + " does not exist.", ex.getMessage());
    }
    /**
     * test delete manager with valid id
     */
    @Test
    public void testDeleteManagerByValidId() {
        // Arrange
        String email="tom@gmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Manager existingManager= new Manager(aPerson);

        when(mockRepo.findManagerByRoleId(ID)).thenReturn(existingManager);

        // Act
        service.deleteManager(ID);

        // Assert
        verify(mockRepo, times(1)).delete(existingManager);
    }
    /**
     * test delete manager with invalid id
     */
    @Test
    public void testDeleteManagerByInvalidId() {

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.deleteManager(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Manager with ID " + ID + " does not exist.", ex.getMessage());
    }
}