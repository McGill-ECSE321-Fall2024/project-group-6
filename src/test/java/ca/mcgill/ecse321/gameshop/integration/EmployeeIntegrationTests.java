package ca.mcgill.ecse321.gameshop.integration;
/**
 * @author Joseph
 */

import java.util.List;

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
import ca.mcgill.ecse321.gameshop.dto.EmployeeListDto;
import ca.mcgill.ecse321.gameshop.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.gameshop.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.gameshop.repository.EmployeeRepository;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private EmployeeRepository repo;
    @Autowired
    private PersonRepository personRepo;

    private static final String VALID_NAME = "Bob";
    private static final String VALID_EMAIL = "jordaniel@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "+1(514)1234567";
    private int id;

    /**
     * clear all repositories after all tests
     */
    @AfterAll
    public void clearDatabase() {
        repo.deleteAll();
        personRepo.deleteAll();
    }

    /**
     * Valid creation of an employee
     */
    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidEmployee() {
        // Arrange
        EmployeeRequestDto request = new EmployeeRequestDto(VALID_NAME, VALID_EMAIL,  VALID_PHONE, VALID_PASSWORD,null,true);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employees", request, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.id = response.getBody().getEmployeeId();

        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
    }

    /**
     * Get the created employee with a valid id
     */
    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetEmployeeByValidId() {
        // Arrange
        String url = String.format("/employees/%d", this.id);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.getForEntity(url,EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.id, response.getBody().getEmployeeId());
        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
    }
    /**
     * Get the created employee with an invalid id
     */
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

    /**
     * update the created employee with a valid id
     */
    @Test
    @Order(4)
    public void testUpdateEmployeeByValidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedjordaniel@mail.mcgill.ca";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654521";


        EmployeeRequestDto updatedEmployeeDto = new EmployeeRequestDto(updatedName, updatedEmail, updatedPhone, updatedPassword,null,true);
        String url = String.format("/employees/%d", this.id);

        // Act
        client.put(url, updatedEmployeeDto);


        ResponseEntity<EmployeeResponseDto> response = client.getForEntity(url, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedName, response.getBody().getUsername());
        assertEquals(updatedPhone, response.getBody().getPhone());

    }
    /**
     * update the created employee with an invalid id
     */
    @Test
    @Order(5)
    public void testUpdateEmployeeByInvalidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedjordancing@mail.mcgill.ca";
        String updatedPhone = "+1(514)7654521";
        String updatedPassword = "newpassword123";

        String url = String.format("/employees/%d", -1);
       EmployeeRequestDto updatedEmployeeDto = new EmployeeRequestDto(updatedName, updatedEmail, updatedPhone, updatedPassword,null,true);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedEmployeeDto), EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    /**
     * add a task to the created employee
     */
    @Test
    @Order(6)
    public void testAddTaskToEmployee() {
        // Arrange
        String url = String.format("/employees/%d/tasks", this.id);
        String taskToAdd = "Do Z";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(taskToAdd, headers);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, EmployeeResponseDto.class);

        // Assert
        assertEquals(taskToAdd,response.getBody().getAssignedTasks().get(0));

    }
    /**
     * add a task to the created employee with an invalid id
     */
    @Test
    @Order(7)
    public void testAddTaskToEmployeeWithInvalidId() {
        // Arrange
        String url = String.format("/employees/%d/tasks", -1);
        String taskToAdd = "Do Z";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(taskToAdd, headers);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, EmployeeResponseDto.class);
        //Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Create another employee and get all employee (there are only 2 employees).
     */
    @Test
    @Order(8)
    public void testGetAllEmployees() {
        // Arrange
        String email2=VALID_EMAIL+"123";
        EmployeeRequestDto request = new EmployeeRequestDto("Joe", email2,  "514 555 7777", "0987654321",null,true);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employees", request, EmployeeResponseDto.class);


        // Act2
        ResponseEntity<EmployeeListDto> response2 = client.getForEntity("/employees", EmployeeListDto.class);

        //Assert
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        List<EmployeeResponseDto> employees = response2.getBody().getEmployees();

        assertEquals( "UpdatedBob", employees.get(0).getUsername());
        assertEquals("Joe", employees.get(1).getUsername());

        assertEquals("+1(514)7654521", employees.get(0).getPhone());
        assertEquals("514 555 7777", employees.get(1).getPhone());

        assertEquals("updatedjordaniel@mail.mcgill.ca", employees.get(0).getEmail());
        assertEquals(email2, employees.get(1).getEmail());

    }
    /**
     * deactivate the created employee with a valid id
     */
    @Test
    @Order(9)
    public void testDeactivateEmployeeByValidId() {
        // Arrange
        String url = String.format("/employees/deactivate/%d", this.id);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.exchange(url, HttpMethod.PUT, null, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String url2 = String.format("/employee/%d", this.id);
        ResponseEntity<EmployeeResponseDto> deactivatedEmployee = client.getForEntity(url2, EmployeeResponseDto.class);
        assertEquals(false, deactivatedEmployee.getBody().getIsActivated());
    }
    /**
     * deactivate the created employee with an invalid id
     */
    @Test
    @Order(10)
    public void testDeactivateEmployeeByInvalidId() {
        // Arrange
        String url = String.format("/employees/deactivate/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.PUT, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    /**
     * invalid creation of an employee
     */
    @Test
    @Order(11)
    public void testCreateEmployeeWithInvalidPassword() {
        // Arrange
        String email="zouz@mcgill.ca";
        EmployeeRequestDto request = new EmployeeRequestDto(VALID_NAME, email,  VALID_PHONE, "123",null,true);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employees", request, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.LENGTH_REQUIRED, response.getStatusCode());

    }
    
     
}