package ca.mcgill.ecse321.gameshop.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
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
import ca.mcgill.ecse321.gameshop.repository.*;
import ca.mcgill.ecse321.gameshop.dto.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository customerRepo;

    private int customerId;
    private static final String VALID_NAME = "Jane";
    private static final String VALID_EMAIL = "jane@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "6138548577";
    private static final String VALID_SHIPPING_ADDRESS = "123 Sherbrooke Street";

    private static final String NEW_NAME = "Jack";
    private static final String NEW_EMAIL = "jack@mail.mcgill.ca";
    private static final String NEW_PASSWORD = "825456bdja";
    private static final String NEW_PHONE = "852741963";
    private static final String NEW_SHIPPING_ADDRESS = "321 Sherbrooke Street";

    @AfterAll
    public void clearDatabase() {
        customerRepo.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void CreateValidCustomer() {
        // Arrange
        CustomerRequestDto request = new CustomerRequestDto(VALID_SHIPPING_ADDRESS, VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_PASSWORD);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customer", request, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.customerId = response.getBody().getCustomerId();
    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetCustomerByValidId() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.customerId, response.getBody().getCustomerId());
    }

    @SuppressWarnings("null")
    @Test
    @Order(3)
    public void testGetCustomerByInvalidId() {
        // Arrange
        String url = String.format("/customer/%d", -1);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(4)
    public void testUpdateCustomer() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the updated details
        CustomerRequestDto updatedDetails = new CustomerRequestDto(NEW_SHIPPING_ADDRESS, NEW_NAME, NEW_EMAIL, NEW_PHONE, NEW_PASSWORD);
        HttpEntity<CustomerRequestDto> entity = new HttpEntity<>(updatedDetails, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDetails.getShippingAddress(), response.getBody().getShippingAddress());
    }

    @SuppressWarnings("null")
    @Test
    @Order(5)
    public void testDeleteCustomerByValidId() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the Customer was actually deleted by trying to fetch it again
        ResponseEntity<CustomerResponseDto> deletedCustomer = client.getForEntity(url, CustomerResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedCustomer.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(6)
    public void testDeleteCustomerByInvalidId() {
        // Arrange
        String url = String.format("/customer/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
