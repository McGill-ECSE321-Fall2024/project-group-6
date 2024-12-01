package ca.mcgill.ecse321.gameshop.integration;
/**
 * @author Joseph and Marine
 */

import ca.mcgill.ecse321.gameshop.GameshopApplication;
import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.repository.*;
import ca.mcgill.ecse321.gameshop.service.PaymentService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private  PaymentRepository paymentRepo;


    private static final String VALID_NAME = "Bob";
    private static final String VALID_EMAIL = "jordanielsonionio@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "+1(514)1234567";
    private static final String VALID_ADDRESS = "678 Sherbrooke west";
    private static final String VALID_BILLING_ADDRESS = "555 Sherbrooke West, Montreal";
    private static final long VALID_CREDIT_CARD_NUMBER = 1234567812345678L;
    private static final String VALID_EXPIRATION_DATE = "04/26";
    private static final int VALID_CVC = 123;
    private static final String VALID_BILLING_ADDRESS2 = "444 Sherbrooke West, Montreal";
    private static final String VALID_BILLING_ADDRESS3 = "333 Sherbrooke West, Montreal";
    private int id;
    private int id2;
    private int catID;
    private int payment1Id;
    private int payment2Id;
    private int payment3Id;
    private int gameid;
    /**
     * Clear all used repositories after end of tests
     */
    @AfterAll
    public void clearDatabase() {
        paymentRepo.deleteAll();
        repo.deleteAll();
        personRepo.deleteAll();
        gameRepo.deleteAll(); //to be used for game addition into cart
        categoryRepo.deleteAll();

    }
    @BeforeAll
    public void setup() {
        // This ensures categories are available for testing
        Category category1 = new Category();
        category1.setCategoryName("Action");
        categoryRepo.save(category1);
        this.catID=category1.getCategoryId();
    }

    /**
     * Create a valid customer
     */
    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidCustomer() {
        // Arrange

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
    /**
     * Create a valid game to later add to the customer cart (part of the scenario)
     */
    @Test
    @Order(2)
    public void testCreateValidGame() {
        List<Integer> categoryId = List.of(this.catID);


        GameRequestDto request = new GameRequestDto("FC 24", "Soccer Game", 60.0F, 2, "https://nothing",true,false,-5F,categoryId);

        // Act
        ResponseEntity<GameResponseDto> response = client.postForEntity("/customers/test", request, GameResponseDto.class);
        this.gameid=response.getBody().getGameId();
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //this.id = response.getBody().getCustomerId();

        assertEquals("FC 24", response.getBody().getName());


    }

    /**
     * get the created customer by a valid id
     */
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

    /**
     * get the created customer by an invalid id
     */
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

    /**
     * update the created customer by a valid id
     */
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

    /**
     * update the created customer by an invalid id
     */
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
    /**
     * Create a new customer, then get all customers (2 in total)
     */
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
    /**
     * add game to the first customer's cart
     */
    @Test
    @Order(8)
    public void testAddGameToCustomerCart() {
        // Arrange

        String url = String.format("/customers/%d/cart/add/%d", this.id,this.gameid);
        Game gameToAdd = gameRepo.findGameByGameId(gameid);


        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, null, CustomerResponseDto.class);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameToAdd.getName(), response.getBody().getCart().get(0).getName());

    }
    /**
     * add game to the first customer's cart with invalid id
     */
    @Test
    @Order(9)
    public void testAddGameToCustomerCartWithInvalidId() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/cart/add/%d", -1,this.gameid);


        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, null, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    /**
     * add game to the first customer's wishlist with valid id
     */
    @Test
    @Order(10)
    public void testAddGameToCustomerWishlist() {
        // Arrange
        String url = String.format("/customers/%d/wishlist/add/%d", this.id,this.gameid);
        Game gameToAdd=gameRepo.findGameByGameId(gameid);
        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, null, CustomerResponseDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gameToAdd.getName(), response.getBody().getWishlist().get(0).getName());

    }
    /**
     * add game to the first customer's wishlist with invalid id
     */
    @Test
    @Order(11)
    public void testAddGameToCustomerWishlistWithInvalidId() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/wishlist/add/%d", -1,this.gameid);
        Game gameToAdd = gameRepo.findGameByGameId(gameid);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, null, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    /**
     * remove game to the first customer's cart with valid id
     */
    @Test
    @Order(12)
    public void testDeleteGameFromCustomerCart() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/cart/%d", this.id, this.gameid);


        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, null, CustomerResponseDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getCart().size());

    }
    /**
     * remove game to the first customer's cart with invalid id
     */
    @Test
    @Order(13)
    public void testDeleteGameFromCustomerCartWithInvalidId() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/cart/game/%d", -1,this.gameid);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, null, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    /**
     * remove game to the first customer's wishlist with valid id
     */
    @Test
    @Order(14)
    public void testDeleteGameFromCustomerWishlist() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/wishlist/%d", this.id,this.gameid);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, null, CustomerResponseDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getWishlist().size());

    }
    /**
     * remove game to the first customer's wishlist with invalid id
     */
    @Test
    @Order(15)
    public void testDeleteGameTFromCustomerWishlistWithInvalidId() {
        // Arrange
        // Arrange
        String url = String.format("/customers/%d/wishlist/%d", -1,this.gameid);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, null, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    /**
     * Tests creating a valid payment.
     *
     * @author Annabelle Huynh-Rondeau
     * @return void
     * @throws AssertionError if the response is invalid.
     */
    @Test
    @Order(16)
    public void testCreateSecondValidCustomer() {
        // Arrange

        CustomerRequestDto request = new CustomerRequestDto(VALID_ADDRESS, VALID_NAME, "VALID_EMAIL2@gmail.com", VALID_PHONE, VALID_PASSWORD, null, null);

        // Act
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        this.id2 = response.getBody().getCustomerId();

        assertEquals(VALID_NAME, response.getBody().getUsername());
        assertEquals("VALID_EMAIL2@gmail.com", response.getBody().getEmail());
        assertEquals(VALID_PHONE, response.getBody().getPhone());
        assertEquals(VALID_ADDRESS, response.getBody().getShippingAddress());
    }


    @Test
    @Order(17)
    public void testCreateValidPayments() {
        // Arrange
        String url1 = String.format("/payment/%d", this.id);
        String url2 = String.format("/payment/%d", this.id2);


        PaymentRequestDto request = new PaymentRequestDto(VALID_BILLING_ADDRESS, VALID_CREDIT_CARD_NUMBER, VALID_EXPIRATION_DATE, VALID_CVC);
        PaymentRequestDto request2 = new PaymentRequestDto(VALID_BILLING_ADDRESS2, VALID_CREDIT_CARD_NUMBER, VALID_EXPIRATION_DATE, VALID_CVC);
        PaymentRequestDto request3 = new PaymentRequestDto(VALID_BILLING_ADDRESS3, VALID_CREDIT_CARD_NUMBER, VALID_EXPIRATION_DATE, VALID_CVC);
        // Act
        ResponseEntity<PaymentResponseDto> response1 = client.postForEntity(url1, request, PaymentResponseDto.class);
        ResponseEntity<PaymentResponseDto> response2 = client.postForEntity(url1, request2, PaymentResponseDto.class);
        ResponseEntity<PaymentResponseDto> response3 = client.postForEntity(url2, request3, PaymentResponseDto.class);

        // Assert
        assertNotNull(response1);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        this.payment1Id = response1.getBody().getPaymentId();
        PaymentResponseDto createdPayment = response1.getBody();
        assertEquals(VALID_BILLING_ADDRESS, createdPayment.getBillingAddress());
        assertTrue(createdPayment.getPaymentId() > 0, "Response should have a positive ID.");

        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        this.payment2Id = response2.getBody().getPaymentId();
        PaymentResponseDto createdPayment2 = response2.getBody();
        assertEquals(VALID_BILLING_ADDRESS2, createdPayment2.getBillingAddress());

        assertTrue(createdPayment2.getPaymentId() > 0, "Response should have a positive ID.");

        assertNotNull(response3);
        assertEquals(HttpStatus.OK, response3.getStatusCode());
        assertNotNull(response3.getBody());
        this.payment3Id = response3.getBody().getPaymentId();
        PaymentResponseDto createdPayment3 = response3.getBody();
        assertEquals(VALID_BILLING_ADDRESS3, createdPayment3.getBillingAddress());

        assertTrue(createdPayment3.getPaymentId() > 0, "Response should have a positive ID.");
    }



    @Test
    @Order(18)
    public void testGetAllCustomerPaymentMethods() {
        // Arrange
        String url = String.format("/customers/%d/payments", this.id);

        // Act
        ResponseEntity<PaymentListDto> response = client.getForEntity(url, PaymentListDto.class);



        // Assert
        assertNotNull(response);
        assertEquals(2,response.getBody().getPayments().size());
        assertEquals(VALID_BILLING_ADDRESS,response.getBody().getPayments().get(0).getBillingAddress());
        assertEquals(VALID_BILLING_ADDRESS2,response.getBody().getPayments().get(1).getBillingAddress());
        //  paymentRepo.deleteAll();
    }



    /**
     * delete the first customer with valid a id
     */
    @Test
    @Order(19)
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
    /**
     * delete the first customer with an invalid id
     */
    @Test
    @Order(20)
    public void testDeleteCustomerByInvalidId() {
        // Arrange
        String url = String.format("/customers/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    /**
     * Invalid creation of a customer
     */
    @Test
    @Order(21)
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