package ca.mcgill.ecse321.gameshop.integration;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.model.Manager;
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
import org.springframework.http.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.gameshop.repository.*;
import ca.mcgill.ecse321.gameshop.GameshopApplication;
import ca.mcgill.ecse321.gameshop.dto.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository repo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private GameRepository gameRepo;

    private static final String VALID_NAME = "Bob";
    private static final String VALID_EMAIL = "jordanielson@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "+1(514)1234567";
    private static final String VALID_ADDRESS = "123 Sherbrooke west";


    private int id;

    @AfterAll
    public void clearDatabase() {
        repo.deleteAll();
        personRepo.deleteAll();
        gameRepo.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidCustomer() {
        // Arrange


        //aCreator, Category... allCategories

        CustomerRequestDto request = new CustomerRequestDto(VALID_ADDRESS, VALID_NAME, VALID_EMAIL, VALID_PHONE, VALID_PASSWORD, null, null);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.id = response.getBody().getCustomerId();

        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
        assertEquals(VALID_ADDRESS, response.getBody().getShippingAddress());

    }

    @Test
    @Order(2)
    public void testCreateValidGame() {


        GameRequestDto2 request = new GameRequestDto2("FC 24", "Soccer Game", 60.0F, 2, "https://nothing");

        // Act
        ResponseEntity<GameResponseDto> response = client.postForEntity("/customers/test", request, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //this.id = response.getBody().getCustomerId();

        assertEquals("FC 24", response.getBody().getName());


    }


    @SuppressWarnings("null")
    @Test
    @Order(3)
    public void testGetCustomerByValidId() {
        // Arrange
        String url = String.format("/customers/%d", this.id);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.id, response.getBody().getCustomerId());
        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
    }


    @Test
    @Order(4)
    public void testGetCustomerByInvalidId() {
        // Arrange
        String url = String.format("/customers/%d", -1);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @SuppressWarnings("null")
    @Test
    @Order(5)
    public void testUpdateCustomerByValidId() {
        // Arrange
        String updatedName = "UpdatedBab";
        String updatedEmail = "updatedjordanielson1@mail.mcgill.ca";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654521";
        String updatedAddress = "123 Sherbrooke East";


        CustomerRequestDto updatedCustomerDto = new CustomerRequestDto(updatedAddress, updatedName, updatedEmail, updatedPhone, updatedPassword, null, null);
        String url = String.format("/customers/%d", this.id);

        // Act
        client.put(url, updatedCustomerDto);


        ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedName, response.getBody().getUsername());
        assertEquals(updatedPhone, response.getBody().getPhone());
        assertEquals(updatedEmail, response.getBody().getEmail());
        assertEquals(updatedAddress, response.getBody().getShippingAddress());

    }


    @Test
    @Order(6)
    public void testUpdateCustomerByInvalidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedjordancing@mail.mcgill.ca";
        String updatedPhone = "+1(514)7654521";
        String updatedPassword = "newpassword123";
        String updatedAddress = "123 Sherbrooke East";

        String url = String.format("/customers/%d", -1);
        CustomerRequestDto updatedCustomerDto = new CustomerRequestDto(updatedAddress, updatedName, updatedEmail, updatedPhone, updatedPassword, null, null);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedCustomerDto), CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testGetAllCustomers() {
        // Arrange
        String email2 = VALID_EMAIL + "123";
        CustomerRequestDto request = new CustomerRequestDto("124 Sherbrooke West", "Joe", email2, "514 555 7777", "0987654321", null, null);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request, CustomerResponseDto.class);


        // Act2
        ResponseEntity<CustomerListDto> response2 = client.getForEntity("/customers", CustomerListDto.class);

        //Assert
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        List<CustomerResponseDto> customers = response2.getBody().getCustomers();

        assertEquals("UpdatedBab", customers.get(0).getUsername());
        assertEquals("Joe", customers.get(1).getUsername());

        assertEquals("+1(514)7654521", customers.get(0).getPhone());
        assertEquals("514 555 7777", customers.get(1).getPhone());

        assertEquals("123 Sherbrooke East", customers.get(0).getShippingAddress());
        assertEquals("124 Sherbrooke West", customers.get(1).getShippingAddress());

        assertEquals("updatedjordanielson1@mail.mcgill.ca", customers.get(0).getEmail());
        assertEquals(email2, customers.get(1).getEmail());

    }

    @Test
    @Order(8)
    public void testAddGameToCustomerCart() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/cart", this.id);
        String gameToAdd = "FC 24";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(gameToAdd, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameToAdd, response.getBody().getCart().get(0).getName());

    }

    @Test
    @Order(9)
    public void testAddGameToCustomerCartWithInvalidId() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/cart", -1);
        String gameToAdd = "FC 24";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(gameToAdd, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    @Order(10)
    public void testAddGameToCustomerWishlist() {
        // Arrange
        String url = String.format("/customers/%d/wishlist", this.id);
        String gameToAdd = "FC 24";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(gameToAdd, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameToAdd, response.getBody().getWishlist().get(0).getName());

    }

    @Test
    @Order(11)
    public void testAddGameToCustomerWishlistWithInvalidId() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/wishlist", -1);
        String gameToAdd = "FC 24";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(gameToAdd, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    @Order(12)
    public void testDeleteGameFromCustomerCart() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/cart/game", this.id);
        String gameToAdd = "FC 24";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(gameToAdd, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getCart().size());

    }
    @Test
    @Order(13)
    public void testDeleteGameFromCustomerCartWithInvalidId() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/cart/game", -1);
        String gameToAdd = "FC 24";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(gameToAdd, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    @Order(14)
    public void testDeleteGameFromCustomerWishlist() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/wishlist/game", this.id);
        String gameToAdd = "FC 24";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(gameToAdd, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getWishlist().size());

    }
    @Test
    @Order(15)
    public void testDeleteGameTFromCustomerWishlistWithInvalidId() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/wishlist/game", -1);
        String gameToAdd = "FC 24";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(gameToAdd, headers);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, entity, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    @Test
    @Order(16)
    public void testDeleteCustomerByValidId() {
        // Arrange
        String url = String.format("/customers/%d", this.id);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the manager was actually deleted by trying to fetch it again
        ResponseEntity<CustomerResponseDto> deletedCustomer = client.getForEntity(url, CustomerResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedCustomer.getStatusCode());
    }

    @Test
    @Order(17)
    public void testDeleteCustomerByInvalidId() {
        // Arrange
        String url = String.format("/customers/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    @Order(18)
    public void testCreateCustomerWithInvalidPassword() {
        // Arrange
        String email="zouzou@mcgill.ca";
        CustomerRequestDto request = new CustomerRequestDto(VALID_ADDRESS,VALID_NAME, email,  VALID_PHONE, "123",null,null);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.LENGTH_REQUIRED, response.getStatusCode());

    }


}

