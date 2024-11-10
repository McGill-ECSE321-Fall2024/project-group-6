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
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.service.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private EmployeeRepository employeeRepo;

    private int employeeId;
    private static final String VALID_NAME = "Jane";
    private static final String VALID_EMAIL = "jane@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "6138548577";

    private static final String NEW_NAME = "Jack";
    private static final String NEW_EMAIL = "jack@mail.mcgill.ca";
    private static final String NEW_PASSWORD = "825456bdja";
    private static final String NEW_PHONE = "852741963";

    @AfterAll
    public void clearDatabase() {
        employeeRepo.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void CreateValidEmployee() {
        // Arrange
        EmployeeRequestDto request = new EmployeeRequestDto(VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_PASSWORD);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employees", request, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.employeeId = response.getBody().getEmployeeId();
    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetEmployeeByValidId() {
        // Arrange
        String url = String.format("/employees/%d", this.employeeId);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.getForEntity(url, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.employeeId, response.getBody().getEmployeeId());
    }

    @SuppressWarnings("null")
    @Test
    @Order(3)
    public void testGetEmployeeByInvalidId() {
        // Arrange
        String url = String.format("/employees/%d", -1);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.getForEntity(url, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(4)
    public void testUpdateEmployee() {
        // Arrange
        String url = String.format("/employees/%d", this.employeeId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the updated details
        EmployeeRequestDto updatedDetails = new EmployeeRequestDto(NEW_NAME, NEW_EMAIL, NEW_PHONE, NEW_PASSWORD);
        HttpEntity<EmployeeRequestDto> entity = new HttpEntity<>(updatedDetails, headers);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDetails.getEmail(), response.getBody().getEmail());
    }

    @SuppressWarnings("null")
    @Test
    @Order(5)
    public void testDeleteEmployeeByValidId() {
        // Arrange
        String url = String.format("/employees/%d", this.employeeId);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the Employee was actually deleted by trying to fetch it again
        ResponseEntity<EmployeeResponseDto> deletedEmployee = client.getForEntity(url, EmployeeResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedEmployee.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(6)
    public void testDeleteEmployeeByInvalidId() {
        // Arrange
        String url = String.format("/employees/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(7)
    public void testAssignTaskToEmployee() {
        // Arrange
        String url = String.format("/employees/%d/tasks", this.employeeId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Setting up a task to add to employee
        String task = "Process new orders";
        HttpEntity<String> entity = new HttpEntity<>(task, headers);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody().getTasks().get(0));
    }

    @SuppressWarnings("null")
    @Test
    @Order(8)
    public void testGetTasksForEmployee() {
        // Arrange
        String url = String.format("/employees/%d/tasks", this.employeeId);

        // Act
        ResponseEntity<List> response = client.exchange(url, HttpMethod.GET, null, List.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());  // Should contain the task we assigned earlier
    }
}