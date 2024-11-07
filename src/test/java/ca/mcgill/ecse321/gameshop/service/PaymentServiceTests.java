package ca.mcgill.ecse321.gameshop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;

@SpringBootTest
public class PaymentServiceTests {
    @Mock
    private PaymentRepository repo;
    @InjectMocks
    private PaymentService service;

    private static final String BILLING_ADDRESS = "555 Sherbrooke West, Montreal";
    private static final long CREDIT_CARD_NUMBER = 1111222233334444L;
    private static final String EXPIRATION_DATE = "04/27";
    private static final int CVC = 345;
    private static final int ID = 10;

    @Test
    public void testCreateValidPayment() {
        //Arrange
        when(repo.save(any(Payment.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        //Act
        Payment createdPayment = service.createPayment(BILLING_ADDRESS, CREDIT_CARD_NUMBER, EXPIRATION_DATE, CVC);

        //Assert
        assertNotNull(createdPayment);
        assertEquals(BILLING_ADDRESS, createdPayment.getBillingAddress());
        assertEquals(CREDIT_CARD_NUMBER, createdPayment.getCreditCardNb());
        assertEquals(EXPIRATION_DATE, createdPayment.getExpirationDate());
        assertEquals(CVC, createdPayment.getCvc());
        verify(repo, times(1)).save(createdPayment);
    }

    @Test
    public void testGetPaymentByValidId() {
        //Arrange
        when(repo.findPaymentByPaymentId(ID)).thenReturn(new Payment(BILLING_ADDRESS, CREDIT_CARD_NUMBER, EXPIRATION_DATE, CVC));

        //Act
        Payment p = service.getPaymentById(ID);

        //Assert
        assertNotNull(p);
        assertEquals(BILLING_ADDRESS, p.getBillingAddress());
        assertEquals(CREDIT_CARD_NUMBER, p.getCreditCardNb());
        assertEquals(EXPIRATION_DATE, p.getExpirationDate());
        assertEquals(CVC, p.getCvc());
    }

    @Test
    public void testGetPaymentByInvalidId() {
        //Arrange
        //Act
        //Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.getPaymentById(ID));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Payment with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testGetAllPayments() {
        // Arrange
        Payment payment1 = new Payment(BILLING_ADDRESS, CREDIT_CARD_NUMBER, EXPIRATION_DATE, CVC);
        Payment payment2 = new Payment("Address 2", 5555666677778888L, "06/29", 456);
        List<Payment> paymentList = Arrays.asList(payment1, payment2);
        when(repo.findAll()).thenReturn(paymentList);

        // Act
        Iterable<Payment> paymentIterable = service.getAllPayments();
        List<Payment> payments = new ArrayList<>();
        paymentIterable.forEach(payments::add);

        // Assert
        assertNotNull(payments);
        assertEquals(2, payments.size());
        assertEquals(BILLING_ADDRESS, payments.get(0).getBillingAddress());
        assertEquals("Address 2", payments.get(1).getBillingAddress());
    }

    @Test
    public void testUpdatePaymentValidId() {
        // Arrange
        String updatedAddress = "New Address";
        long updatedNumber = 5555666677778888L;
        String updatedDate = "12/30";
        int updatedCvc = 345;
    
        Payment existingPayment = new Payment(BILLING_ADDRESS, CREDIT_CARD_NUMBER, EXPIRATION_DATE, CVC);
        when(repo.findPaymentByPaymentId(ID)).thenReturn(existingPayment);
        
        // Simply return the updated payment object without modifying it in the mock
        when(repo.save(any(Payment.class))).thenReturn(existingPayment);
    
        // Act
        Payment updatedPayment = service.updatePayment(ID, updatedAddress, updatedNumber, updatedDate, updatedCvc);
    
        // Assert
        assertNotNull(updatedPayment);
        assertEquals(updatedAddress, updatedPayment.getBillingAddress());
        assertEquals(updatedNumber, updatedPayment.getCreditCardNb());
        assertEquals(updatedDate, updatedPayment.getExpirationDate());
        assertEquals(updatedCvc, updatedPayment.getCvc());
    
        verify(repo, times(1)).save(existingPayment);
    }

    @Test
    public void testUpdatePaymentInvalidId() {
        // Arrange
        // Act
        // Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.updatePayment(ID, BILLING_ADDRESS, CREDIT_CARD_NUMBER, EXPIRATION_DATE, CVC));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Payment with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testDeletePaymentByValidId() {
        // Arrange
        Payment existingPayment = new Payment(BILLING_ADDRESS, CREDIT_CARD_NUMBER, EXPIRATION_DATE, CVC);

        when(repo.findPaymentByPaymentId(ID)).thenReturn(existingPayment);

        // Act
        service.deletePayment(ID);

        // Assert
        verify(repo, times(1)).delete(existingPayment);
    }

    @Test
    public void testDeletePaymentByInvalidId() {
         // Arrange
        // Act
        // Assert
		GameShopException ex = assertThrows(GameShopException.class,
        () -> service.deletePayment(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Payment with ID " + ID + " does not exist.", ex.getMessage());
    }
}