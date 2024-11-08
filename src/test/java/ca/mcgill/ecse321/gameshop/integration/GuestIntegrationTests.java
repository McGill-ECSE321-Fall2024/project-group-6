package ca.mcgill.ecse321.gameshop.integration;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gameshop.GameshopApplication;
import ca.mcgill.ecse321.gameshop.dto.GuestResponseDto;
import ca.mcgill.ecse321.gameshop.repository.GuestRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GuestIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private GuestRepository repo;

    private int id;

    @AfterAll
    public void clearDatabase() {
        repo.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidGuest() {
        // Arrange: Pass an empty JSON object instead of null
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Use an empty JSON object for the GuestRequestDto since the body is optional in this case
        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        // Act
        ResponseEntity<GuestResponseDto> response = client.postForEntity("/guest", entity, GuestResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.id = response.getBody().getGuestId();
    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetGuestByValidId() {
        // Arrange
        String url = String.format("/guest/%d", this.id);

        // Act
        ResponseEntity<GuestResponseDto> response = client.getForEntity(url, GuestResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.id, response.getBody().getGuestId());
    }

    @Test
    @Order(3)
    public void testGetGuestByInvalidId() {
        // Arrange
        String url = String.format("/guest/%d", -1);

        // Act
        ResponseEntity<GuestResponseDto> response = client.getForEntity(url, GuestResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(4)
    public void testDeleteGuestByValidId() {
        // Arrange
        String url = String.format("/guest/%d", this.id);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the Guest was actually deleted by trying to fetch it again
        ResponseEntity<GuestResponseDto> deletedGuest = client.getForEntity(url, GuestResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedGuest.getStatusCode());
    }

    @Test
    @Order(5)
    public void testDeleteGuestByInvalidId() {
        // Arrange
        String url = String.format("/guest/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}