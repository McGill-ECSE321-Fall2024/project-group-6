package integration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gameshop.GameshopApplication;
import ca.mcgill.ecse321.gameshop.dto.PersonRequestDto;
import ca.mcgill.ecse321.gameshop.dto.PersonResponseDto;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PersonIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private PersonRepository repo;

    private static final String VALID_NAME = "Bob";
    private static final String VALID_EMAIL = "bob@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678";
    private static final String VALID_PHONE = "+1(514)1234567";
    private int id;

    @AfterAll
    public void clearDatabase() {
        repo.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidPerson() {
        // Arrange
        PersonRequestDto request = new PersonRequestDto(VALID_NAME, VALID_EMAIL,  VALID_PASSWORD, VALID_PHONE);

        // Act
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/person", request, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.id = response.getBody().getUserId();
        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetPersonByValidId() {
        // Arrange
        String url = String.format("/person/%d", this.id);

        // Act
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.id, response.getBody().getUserId());
        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
    }

    @Test
    @Order(3)
    public void testGetPersonByInvalidId() {
        // Arrange
        String url = String.format("/person/%d", -1);

        // Act
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(4)
    public void testUpdatePersonByValidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedbob@mail.mcgill.ca";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654521";

        PersonRequestDto updatedPersonDto = new PersonRequestDto(updatedName, updatedEmail, updatedPassword, updatedPhone);
        String url = String.format("/person/%d", this.id);

        // Act
        client.put(url, updatedPersonDto);
    
        // Fetch updated person
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedName, response.getBody().getUsername());
        //assertEquals(updatedEmail, response.getBody().getEmail());
        assertEquals(updatedPhone, response.getBody().getPhone());
    }

    @Test
    @Order(5)
    public void testUpdatePersonByInvalidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedbob@mail.mcgill.ca";
        String updatedPhone = "+1(514)7654521";
        String updatedPassword = "newpassword123";

        String url = String.format("/person/%d", -1);
        PersonRequestDto updatedPersonDto = new PersonRequestDto(updatedName, updatedEmail, updatedPhone, updatedPassword);

        // Act
        ResponseEntity<PersonResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedPersonDto), PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeletePersonByValidId() {
        // Arrange
        String url = String.format("/person/%d", this.id);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<PersonResponseDto> deletedPerson = client.getForEntity(url, PersonResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedPerson.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeletePersonByInvalidId() {
        // Arrange
        String url = String.format("/person/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}