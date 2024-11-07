package ca.mcgill.ecse321.gameshop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class PersonServiceTests {
    @Mock
    private PersonRepository repo;
    @InjectMocks
    private PersonService service;

    private static final String VALID_NAME = "Bob";
    private static final String VALID_EMAIL = "bob@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678";
    private static final String VALID_PHONE = "+1(514)1234567";
    private static final int ID = 10;

    @Test
    public void testCreateValidPerson() {
        // Arrange
        // Return object when repo.save() is called
        when(repo.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Person createdPerson = service.createPerson(VALID_NAME, "mario@gmail.com", VALID_PASSWORD, VALID_PHONE);

        // Assert
        assertNotNull(createdPerson);
        assertEquals(VALID_NAME, createdPerson.getUsername());
        assertEquals("mario@gmail.com", createdPerson.getEmail());
        assertEquals(VALID_PASSWORD, createdPerson.getPassword());
        assertEquals(VALID_PHONE, createdPerson.getPhone());

        // Check that save() was called exactly once with given argument
        verify(repo, times(1)).save(createdPerson);
    }

    @Test
    public void testGetPersonByValidId() {
        // Arrange
        when(repo.findPersonByUserId(ID)).thenReturn(new Person(VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_PHONE));

        // Act
        Person p = service.getPersonByUserId(ID);

        // Assert
        assertNotNull(p);
        assertEquals(VALID_NAME, p.getUsername());
        assertEquals(VALID_EMAIL, p.getEmail());
        assertEquals(VALID_PASSWORD, p.getPassword());
        assertEquals(VALID_PHONE, p.getPhone());
    }

    @Test
    public void testGetPersonByInvalidId() {
        // Arrange
        // Act
        // Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.getPersonByUserId(ID));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Person with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testGetAllPeople() {
        // Arrange
        Person p1 = new Person(VALID_NAME, "test@mail.mcgill.ca", VALID_PASSWORD, VALID_PHONE);
        Person p2 = new Person("Alice", "alice@mail.mcgill.ca", "password123", "+1(514)9876543");
        List<Person> personList = Arrays.asList(p1, p2);
        when(repo.findAll()).thenReturn(personList);

        // Act
        Iterable<Person> peopleIterable = service.getAllPeople();
        List<Person> people = new ArrayList<>();
        peopleIterable.forEach(people::add);

        // Assert
        assertNotNull(people);
        assertEquals(2, people.size());
        assertEquals(VALID_NAME, people.get(0).getUsername());
        assertEquals("Alice", people.get(1).getUsername());
    }

    @Test
    public void testUpdatePersonByValidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedbob@mail.mcgill.ca";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654321";
        
        Person existingPerson = new Person(VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_PHONE);
        
        // Simulate finding the person by ID
        when(repo.findPersonByUserId(ID)).thenReturn(existingPerson);
        
        // Simulate the save operation returning the existing (now updated) person
        when(repo.save(any(Person.class))).thenReturn(existingPerson);
    
        // Act
        Person updatedPerson = service.updatePerson(ID, updatedName, updatedEmail, updatedPassword, updatedPhone);
    
        // Assert
        assertNotNull(updatedPerson);
        assertEquals(updatedName, updatedPerson.getUsername());
        assertEquals(updatedEmail, updatedPerson.getEmail());
        assertEquals(updatedPassword, updatedPerson.getPassword());
        assertEquals(updatedPhone, updatedPerson.getPhone());
    
        // Verify that the save method was called exactly once with the updated person
        verify(repo, times(1)).save(existingPerson);
    }
    

    @Test
    public void testUpdatePersonByInvalidId() {
        // Arrange
        // Act
        // Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.updatePerson(ID, VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_PHONE));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Person with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testDeletePersonByValidId() {
        // Arrange
        Person existingPerson = new Person(VALID_NAME, "test@gmail.com", VALID_PASSWORD, VALID_PHONE);
    
        when(repo.findPersonByUserId(ID)).thenReturn(existingPerson);

        // Act
        service.deletePerson(ID);

        // Assert
        verify(repo, times(1)).delete(existingPerson);
    }

    @Test
    public void testDeletePersonByInvalidId() {
        // Arrange
        // Act
        // Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.deletePerson(ID));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Person with ID " + ID + " does not exist.", ex.getMessage());
    }
}
