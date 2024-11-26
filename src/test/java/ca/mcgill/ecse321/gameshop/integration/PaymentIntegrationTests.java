package ca.mcgill.ecse321.gameshop.integration;

import ca.mcgill.ecse321.gameshop.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentRequestDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentResponseDto;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PaymentIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PaymentRepository repo;
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PersonRepository personRepo;


    private static final String VALID_NAME = "Bob";
    private static final String VALID_EMAIL = "jordanielsonionio@mail.mcgill.ca";
    private static final String VALID_PASSWORD = "12345678910";
    private static final String VALID_PHONE = "+1(514)1234567";
    private static final String VALID_ADDRESS = "123 Sherbrooke west";
    private static final String VALID_BILLING_ADDRESS = "555 Sherbrooke West, Montreal";
    private static final long VALID_CREDIT_CARD_NUMBER = 1234567812345678L;
    private static final String VALID_EXPIRATION_DATE = "04/26";
    private static final int VALID_CVC = 123;
    public static final Customer VALID_CUSTOMER = new Customer(); //DO I USE THE CONSTRUCTOR WITH CUSTOMER OR NO??
    private final int INVALID_ID = 0;
    private static final String INVALID_BILLING_ADDRESS = "";
    private static final long INVALID_CREDIT_CARD_NUMBER = 1234567L;
    private static final String INVALID_EXPIRATION_DATE = "04/22";
    private static final String INVALID_CVC = "12345";
    private int validId;
    private int id;
    @AfterAll
    public void clearDatabase() {

        repo.deleteAll();
        customerRepo.deleteAll();
        personRepo.deleteAll();
    }

    /**
     * @author Joseph
     * Create a customer to be used later in other tests
     */
    @Test
    @Order(1)
    public void testCreateValidCustomer2() {
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
     * Tests creating a valid payment.
     *
     * @author Annabelle Huynh-Rondeau
     * @return void
     * @throws AssertionError if the response is invalid.
     */
    @Test
    @Order(2)
    public void testCreateValidPayment() {
        // Arrange
        String url = String.format("/payment/%d", this.id);


        PaymentRequestDto request = new PaymentRequestDto(VALID_BILLING_ADDRESS, VALID_CREDIT_CARD_NUMBER, VALID_EXPIRATION_DATE, VALID_CVC);

        // Act
        ResponseEntity<PaymentResponseDto> response = client.postForEntity(url, request, PaymentResponseDto.class);


        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        this.validId = response.getBody().getPaymentId();
        PaymentResponseDto createdPayment = response.getBody();
        assertEquals(VALID_BILLING_ADDRESS, createdPayment.getBillingAddress());
        Payment payment2= repo.findPaymentByPaymentId(validId);
        assertEquals(VALID_NAME,payment2.getCustomer().getPerson().getUsername());
        assertTrue(createdPayment.getPaymentId() > 0, "Response should have a positive ID.");
    }

    /**
     * Tests retrieving a payment by valid ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @return void
     * @throws AssertionError if the response is invalid.
     */
    @SuppressWarnings("null")
    @Test
    @Order(3)
    public void testGetPaymentByValidId() {
        // Arrange
        String url = String.format("/payment/%d", this.validId);

        // Act
        ResponseEntity<PaymentResponseDto> response = client.getForEntity(url, PaymentResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.validId, response.getBody().getPaymentId());
        assertEquals(VALID_BILLING_ADDRESS, response.getBody().getBillingAddress());
    }

    /**
     * Tests retrieving a payment by invalid ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @return void
     * @throws AssertionError if the response is invalid.
     */
    @Test
    @Order(4)
    public void testGetPaymentByInvalidId() {
        // Arrange
        String url = String.format("/payment/%d", -1);

        // Act
        ResponseEntity<PaymentResponseDto> response = client.getForEntity(url, PaymentResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests updating a payment with a valid ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @return void
     * @throws AssertionError if the response is invalid.
     */
    @SuppressWarnings("null")
    @Test
    @Order(5)
    public void testUpdatePaymentByValidId() {
        // Arrange
        String updatedBillingAddress = "565 Sherbrooke West, Montreal";
        long updatedCreditCardNb = 1111111111111111L;
        String updatedExpirationDate = "09/29";
        int updatedCvc = 787;

        Payment updatedPayment = new Payment(updatedBillingAddress, updatedCreditCardNb, updatedExpirationDate, updatedCvc);
        PaymentRequestDto updatedPaymentDto = new PaymentRequestDto(updatedBillingAddress, updatedCreditCardNb, updatedExpirationDate, updatedCvc);
        String url = String.format("/payment/%d", this.validId);

        // Act
        client.put(url, updatedPaymentDto);

        // Fetch updated payment
        ResponseEntity<PaymentResponseDto> response = client.getForEntity(url, PaymentResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBillingAddress, response.getBody().getBillingAddress());
    }

    /**
     * Tests updating a payment with an invalid ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @return void
     * @throws AssertionError if the response is invalid.
     */
    @Test
    @Order(6)
    public void testUpdatePaymentByInvalidId() {
        // Arrange
        String updatedBillingAddress = "565 Sherbrooke West, Montreal";
        long updatedCreditCardNb = 1111111111111111L;
        String updatedExpirationDate = "09/29";
        int updatedCvc = 787;

        String url = String.format("/payment/%d", -1);
        PaymentRequestDto updatedPaymentDto = new PaymentRequestDto(updatedBillingAddress, updatedCreditCardNb, updatedExpirationDate, updatedCvc);

        // Act
        ResponseEntity<PaymentResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedPaymentDto), PaymentResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests deleting a payment by valid ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @return void
     * @throws AssertionError if the response is invalid.
     */
    @Test
    @Order(7)
    public void testDeletePaymentByValidId() {
        // Arrange
        String url = String.format("/payment/%d", this.validId);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the payment was actually deleted by trying to fetch it again
        ResponseEntity<PaymentResponseDto> deletedPayment = client.getForEntity(url, PaymentResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedPayment.getStatusCode());
    }

    /**
     * Tests deleting a payment by invalid ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @return void
     * @throws AssertionError if the response is invalid.
     */
    @Test
    @Order(8)
    public void testDeletePaymentByInvalidId() {
        // Arrange
        String url = String.format("/payment/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
