package ca.mcgill.ecse321.gameshop.integration;
/**
 * @author Joseph
 */
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
import ca.mcgill.ecse321.gameshop.repository.*;
import ca.mcgill.ecse321.gameshop.GameshopApplication;
import ca.mcgill.ecse321.gameshop.dto.*;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ManagerIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private ManagerRepository repo;
@Autowired
private PersonRepository personRepo;

    private static final String VALID_NAME = "Bob";
    private static final String VALID_EMAIL = "jordan@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "+1(514)1234567";
    private int id;

    /**
     * Clear all repositories after tests end
     */
    @AfterAll
    public void clearDatabase() {
        repo.deleteAll();
        personRepo.deleteAll();
    }

    /**
     * Create a valid manager
     */
    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidManager() {
        // Arrange
       ManagerRequestDto request = new ManagerRequestDto(VALID_NAME, VALID_EMAIL,  VALID_PHONE, VALID_PASSWORD);

        // Act
        ResponseEntity<ManagerResponseDto> response = client.postForEntity("/manager", request, ManagerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.id = response.getBody().getManagerId();
        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
    }
    /**
     * get the created valid manager with a valid id
     */

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetManagerByValidId() {
        // Arrange
        String url = String.format("/manager/%d", this.id);

        // Act
        ResponseEntity<ManagerResponseDto> response = client.getForEntity(url,ManagerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.id, response.getBody().getManagerId());
        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
    }
    /**
     * get the created valid manager with an invalid id
     */
    @Test
    @Order(3)
    public void testGetManagerByInvalidId() {
        // Arrange
        String url = String.format("/manager/%d", -1);

        // Act
        ResponseEntity<ManagerResponseDto> response = client.getForEntity(url, ManagerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    /**
     * update the created valid manager with a valid id
     */
    @SuppressWarnings("null")
    @Test
    @Order(4)
    public void testUpdateManagerByValidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedjordan@mail.mcgill.ca";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654521";

       ManagerRequestDto updatedManagerDto = new ManagerRequestDto(updatedName, updatedEmail, updatedPhone, updatedPassword);
        String url = String.format("/manager/%d", this.id);

        // Act
        client.put(url, updatedManagerDto);


        ResponseEntity<ManagerResponseDto> response = client.getForEntity(url, ManagerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedName, response.getBody().getUsername());
        assertEquals(updatedPhone, response.getBody().getPhone());
    }
    /**
     * update the created valid manager with an invalid id
     */
    @Test
    @Order(5)
    public void testUpdateManagerByInvalidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedjordans@mail.mcgill.ca";
        String updatedPhone = "+1(514)7654521";
        String updatedPassword = "newpassword123";

        String url = String.format("/manager/%d", -1);
        ManagerRequestDto updatedManagerDto = new ManagerRequestDto(updatedName, updatedEmail, updatedPhone, updatedPassword);

        // Act
        ResponseEntity<ManagerResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedManagerDto), ManagerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    /**
     * delete the created valid manager with a valid id
     */
    @Test
    @Order(6)
    public void testDeleteManagerByValidId() {
        // Arrange
        String url = String.format("/manager/%d", this.id);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the manager was actually deleted by trying to fetch it again
        ResponseEntity<ManagerResponseDto> deletedManager = client.getForEntity(url, ManagerResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedManager.getStatusCode());
    }
    /**
     * delete the created valid manager with an invalid id
     */
    @Test
    @Order(7)
    public void testDeleteManagerByInvalidId() {
        // Arrange
        String url = String.format("/manager/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    /**
     * invalid creation of a manager
     */
    @Test
    @Order(8)
    public void testCreateManagerWithInvalidPassword() {
        // Arrange
        String email="z@mcgill.ca";
        ManagerRequestDto request = new ManagerRequestDto(VALID_NAME, email,  VALID_PHONE, "123");

        // Act
        ResponseEntity<ManagerResponseDto> response = client.postForEntity("/manager", request, ManagerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.LENGTH_REQUIRED, response.getStatusCode());

    }
    
     
}