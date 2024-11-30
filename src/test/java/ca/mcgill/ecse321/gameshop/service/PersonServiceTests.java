package ca.mcgill.ecse321.gameshop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import ca.mcgill.ecse321.gameshop.dto.PersonResponseDto;
import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
/**
 * @author Mario and Joseph
 */
@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class PersonServiceTests {
        @Mock
        private PersonRepository mockRepo;
        @InjectMocks
        private PersonService service;

        private static final String VALID_NAME = "Bob";
        private static final String VALID_EMAIL = "bob@mail.mcgill.ca";
        private static final String VALID_PASSWORD = "12345678910";
        private static final String VALID_PHONE = "+1(514)1234567";
        private static final int ID = 10;

        @Test
        public void testCreateValidPerson() {
                // Arrange
                // Return object when repo.save() is called
                when(mockRepo.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

                // Act
                Person createdPerson = service.createPerson(VALID_NAME, "mario@gmail.com", VALID_PASSWORD, VALID_PHONE);

                // Assert
                assertNotNull(createdPerson);
                assertEquals(VALID_NAME, createdPerson.getUsername());
                assertEquals("mario@gmail.com", createdPerson.getEmail());
                assertEquals(VALID_PASSWORD, createdPerson.getPassword());
                assertEquals(VALID_PHONE, createdPerson.getPhone());

                // Check that save() was called exactly once with given argument
                verify(mockRepo, times(1)).save(createdPerson);
        }

        @Test
        public void testCreatePersonWithInvalidPhone() {
                // Arrange
                // Whenever mockRepo.save(p) is called, return p
                // when(mockRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
                String email="qrstuvwxyz@gmail.com";

                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.createPerson(VALID_NAME, email, VALID_PASSWORD, null));
                assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
                assertEquals("Phone number can not be null", ex.getMessage());
        }

        @Test
        public void testCreatePersonWithInvalidUsername() {
                String email="qrstuvwxy@gmail.com";

                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.createPerson(null, email, VALID_PASSWORD, VALID_PHONE));

                assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
                assertEquals("Username can not be null", ex.getMessage());
        }

        @Test
        public void testCreatePersonWithInvalidEmail() {
                // Arrange
                // Whenever mockRepo.save(p) is called, return p
                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.createPerson(VALID_NAME, null, VALID_PASSWORD, VALID_PHONE));
                assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
                assertEquals("Email can not be null", ex.getMessage());
        }

        @Test
        public void testCreatePersonWithInvalidPassword() {
                // Arrange
                // Whenever mockRepo.save(p) is called, return p
                String email="qrstuvwx@gmail.com";

                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.createPerson(VALID_NAME, email, "123", VALID_PHONE));
                assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
                assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
        }

        @Test
        public void testGetPersonByValidId() {
                // Arrange
                when(mockRepo.findPersonByUserId(ID)).thenReturn(new Person(VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_PHONE));

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
                when(mockRepo.findAll()).thenReturn(personList);

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
                String updatedEmail = "updatedAli@mail.mcgill.ca";
                String updatedPassword = "newpassword123";
                String updatedPhone = "+1(514)7654321";

                Person existingPerson = new Person(VALID_NAME, VALID_EMAIL, "test@mcgill.ca", VALID_PHONE);

                // Simulate finding the person by ID
                when(mockRepo.findPersonByUserId(ID)).thenReturn(existingPerson);

                // Simulate the save operation returning the existing (now updated) person
                when(mockRepo.save(any(Person.class))).thenReturn(existingPerson);

                // Act
                Person updatedPerson = service.updatePerson(ID, updatedName, updatedEmail, updatedPassword, updatedPhone);

                // Assert
                assertNotNull(updatedPerson);
                assertEquals(updatedName, updatedPerson.getUsername());
                assertEquals(updatedEmail, updatedPerson.getEmail());
                assertEquals(updatedPassword, updatedPerson.getPassword());
                assertEquals(updatedPhone, updatedPerson.getPhone());

                // Verify that the save method was called exactly once with the updated person
                verify(mockRepo, times(1)).save(existingPerson);
        }

        @Test
        public void testUpdatePersonByInvalidPasswordLength() {
                // Arrange
                String updatedPassword = "123";

                Person existingPerson = new Person(VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_PHONE);

                // Simulate finding the person by ID
                when(mockRepo.findPersonByUserId(ID)).thenReturn(existingPerson);

                // Act
                // Assert
                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.updatePerson(ID, VALID_NAME, VALID_EMAIL, updatedPassword, VALID_PHONE));
                assertEquals(HttpStatus.LENGTH_REQUIRED, ex.getStatus());
                assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
        }

        @Test
        public void testUpdatePersonByNullUsername() {
                String updatedName = null;

                String email = "qrstu@gmail.com";
                Person existingPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);

                when(mockRepo.findPersonByUserId(ID)).thenReturn(existingPerson);

                // Assert
                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.updatePerson(ID, updatedName, VALID_EMAIL, VALID_PASSWORD, VALID_PHONE));
                assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
                assertEquals("Username can not be null", ex.getMessage());
        }

        @Test
        public void testUpdatePersonByNullPhone() {
                // Arrange
                String updatedPhone = null;

                String email = "qrstu@gmail.com";
                Person existingPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);

                when(mockRepo.findPersonByUserId(ID)).thenReturn(existingPerson);

                // Assert
                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.updatePerson(ID, VALID_NAME, VALID_EMAIL, VALID_PASSWORD, updatedPhone));
                assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
                assertEquals("Phone number can not be null", ex.getMessage());
        }

        @Test
        public void testUpdatePersonByNullEmail() {
                // Arrange
                String updatedEmail = null;

                String email = "qrstu@gmail.com";
                Person existingPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);

                when(mockRepo.findPersonByUserId(ID)).thenReturn(existingPerson);

                // Assert
                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.updatePerson(ID, VALID_NAME, updatedEmail, VALID_PASSWORD, VALID_PHONE));
                assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
                assertEquals("Email can not be null", ex.getMessage());
        }

        @Test
        public void testUpdatePersonByInvalidId() {
                // Arrange
                // Act
                // Assert
                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.deletePerson(ID));
                assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
                assertEquals("Person with ID " + ID + " does not exist.", ex.getMessage());
        }

        @Test
        public void testDeletePersonByValidId() {
                // Arrange
                Person existingPerson = new Person(VALID_NAME, "test@gmail.com", VALID_PASSWORD, VALID_PHONE);

                when(mockRepo.findPersonByUserId(ID)).thenReturn(existingPerson);

                // Act
                service.deletePerson(ID);

                // Assert
                verify(mockRepo, times(1)).delete(existingPerson);
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

        @Test
        public void successfulLoginAttempt() {
                when(mockRepo.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

                String email = "abcdefg@render.com";
                Person existingPerson = service.createPerson(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);

                when(mockRepo.findPersonByEmail(email)).thenReturn(existingPerson);
                Person p = service.login(email, VALID_PASSWORD);
                assertEquals(p.getUsername(), VALID_NAME);
                verify(mockRepo, times(1)).save(any(Person.class));
        }

        @Test
        public void testLoginWithInvalidEmail() {
                // Arrange
                when(mockRepo.findPersonByEmail(VALID_EMAIL)).thenReturn(null);

                // Act and Assert
                GameShopException ex = assertThrows(GameShopException.class,
                        () -> service.login(VALID_EMAIL, VALID_PASSWORD));
                assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
                assertEquals("Person with email does not exist.", ex.getMessage());
        }

        @Test
        public void testLoginWithInvalidPassword() {
                // Arrange
                when(mockRepo.findPersonByEmail(VALID_EMAIL)).thenReturn(new Person(VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_PHONE));

                // Act and Assert
                GameShopException ex = assertThrows(GameShopException.class, () -> {
                        service.login(VALID_EMAIL, "wrongPassword");
                });

                assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
                assertEquals("Invalid credentials", ex.getMessage());
        }
}