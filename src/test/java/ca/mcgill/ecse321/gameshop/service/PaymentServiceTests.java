package ca.mcgill.ecse321.gameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
import ca.mcgill.ecse321.gameshop.dto.PaymentResponseDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Arrays;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class PaymentServiceTests {
    @Mock
    private PaymentRepository repo;

    @InjectMocks
    private PaymentService service;

    /**
     * @author Annabelle Huynh-Rondeau
     */
    @Test
    public void testCreateValidPayment() {
        // Arrange
        String billingAddress = "555 Sherbrooke West, Montreal";
        long creditCardNb = 1111222233334444L;
        String exp = "04/27";
        int cvc = 345;
        Payment payment = new Payment(billingAddress, creditCardNb, exp, cvc);

        when(repo.save(any(Payment.class))).thenReturn(payment);

        // Act
        Payment createdPayment = service.createPayment(billingAddress, creditCardNb, exp, cvc);

        // Assert
        assertNotNull(createdPayment);
        assertEquals(billingAddress, createdPayment.getBillingAddress());
        assertEquals(creditCardNb, createdPayment.getCreditCardNb());
        assertEquals(exp, createdPayment.getExpirationDate());
        assertEquals(cvc, createdPayment.getCvc());
        verify(repo, times(1)).save(any(Payment.class));
    }

    @Test
    public void testCreatePaymentWithInvalidBillingAddress() {
        // Arrange
        String billingAddress = ""; // Invalid billing address
        long creditCardNb = 1111222233334444L;
        String exp = "04/27";
        int cvc = 345;

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createPayment(billingAddress, creditCardNb, exp, cvc);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Billing address cannot be empty", exception.getMessage());
    }

    @Test
    public void testCreatePaymentWithInvalidCreditCardNumber() {
        // Arrange
        String billingAddress = "555 Sherbrooke West, Montreal";
        long creditCardNb = 12345L; // Invalid credit card number
        String exp = "04/27";
        int cvc = 345;

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createPayment(billingAddress, creditCardNb, exp, cvc);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Credit card number must be 16 digits", exception.getMessage());
    }

    @Test
    public void testCreatePaymentWithInvalidExpirationDate() {
        // Arrange
        String billingAddress = "555 Sherbrooke West, Montreal";
        long creditCardNb = 1111222233334444L;
        String exp = "04/20"; // Invalid expiration date (in the past)
        int cvc = 345;

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createPayment(billingAddress, creditCardNb, exp, cvc);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Expiration date must be in the future", exception.getMessage());
    }

    @Test
    public void testCreatePaymentWithInvalidCVC() {
        // Arrange
        String billingAddress = "555 Sherbrooke West, Montreal";
        long creditCardNb = 1111222233334444L;
        String exp = "04/27";
        int cvc = 12; // Invalid CVC (only 2 digits)

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.createPayment(billingAddress, creditCardNb, exp, cvc);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("CVC must be 3 digits", exception.getMessage());
    }

    @Test
    public void testReadPaymentByValidId() {
        // Arrange
        int id = 3;
        Payment payment = new Payment("555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 345);
        when(repo.findPaymentByPaymentId(id)).thenReturn(payment);

        // Act
        Payment paymentToGet = service.getPaymentById(id);

        // Assert
        assertNotNull(paymentToGet);
        assertEquals(payment.getBillingAddress(), paymentToGet.getBillingAddress());
        assertEquals(payment.getCreditCardNb(), paymentToGet.getCreditCardNb());
        assertEquals(payment.getExpirationDate(), paymentToGet.getExpirationDate());
        assertEquals(payment.getCvc(), paymentToGet.getCvc());
        verify(repo, times(1)).findPaymentByPaymentId(id);
    }

    @Test
    public void testReadPaymentByInvalidId() {
        // Arrange
        int id = 3;
        when(repo.findPaymentByPaymentId(id)).thenReturn(null);

        // Act & Assert
        GameShopException e = assertThrows(GameShopException.class, () -> service.getPaymentById(id));
        assertEquals("Payment with ID " + id + " does not exist.", e.getMessage());
        verify(repo, times(1)).findPaymentByPaymentId(id);
    }

    @Test
    public void testUpdatePaymentValidId() {
        // Arrange
        int paymentId = 1;
        Payment existingPayment = new Payment("Old Address", 1111222233334444L, "04/27", 123);
        Payment updatedDetails = new Payment("New Address", 5555666677778888L, "12/30", 456);
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto("New Address", 5555666677778888L, "12/30", 456);
        when(repo.findPaymentByPaymentId(paymentId)).thenReturn(existingPayment);
        when(repo.save(any(Payment.class))).thenReturn(updatedDetails);

        // Act
        Payment updatedPayment = service.updatePayment(paymentId, "New Address", 5555666677778888L, "12/30", 456);

        // Assert
        assertNotNull(updatedPayment);
        assertEquals(updatedDetails.getBillingAddress(), updatedPayment.getBillingAddress());
        assertEquals(updatedDetails.getCreditCardNb(), updatedPayment.getCreditCardNb());
        assertEquals(updatedDetails.getExpirationDate(), updatedPayment.getExpirationDate());
        assertEquals(updatedDetails.getCvc(), updatedPayment.getCvc());
        verify(repo, times(1)).findPaymentByPaymentId(paymentId);
        verify(repo, times(1)).save(any(Payment.class));
    }

    @Test
    public void testUpdatePaymentInvalidId() {
        // Arrange
        int invalidPaymentId = 99;
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        when(repo.findPaymentByPaymentId(invalidPaymentId)).thenReturn(null);

        // Act & Assert
        GameShopException e = assertThrows(GameShopException.class, () ->
                service.updatePayment(invalidPaymentId, "New Address", 5555666677778888L, "12/30", 456)
        );
        assertEquals("Payment with ID " + invalidPaymentId + " does not exist.", e.getMessage());
        verify(repo, times(1)).findPaymentByPaymentId(invalidPaymentId);
        verify(repo, times(0)).save(any(Payment.class));
    }

    @Test
    public void testUpdatePaymentWithInvalidBillingAddress() {
        // Arrange
        int paymentId = 1;
        Payment existingPayment = new Payment("555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 345);
        Payment payment = new Payment("", 1111222233334444L, "04/27", 345); // Invalid billing address (empty))
        PaymentRequestDto paymentRequest = new PaymentRequestDto("", 1111222233334444L, "04/27", 345);

        when(repo.findPaymentByPaymentId(paymentId)).thenReturn(existingPayment);

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.updatePayment(paymentId, "", 1111222233334444L, "04/27", 345);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Billing address cannot be empty", exception.getMessage());
        verify(repo, times(1)).findPaymentByPaymentId(paymentId);
    }

    @Test
    public void testUpdatePaymentWithInvalidCreditCardNumber() {
        // Arrange
        int paymentId = 1;
        Payment existingPayment = new Payment("555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 345);
        Payment payment = new Payment("555 Sherbrooke West, Montreal", 12345L, "05/27", 345);
        PaymentRequestDto paymentRequest = new PaymentRequestDto("555 Sherbrooke West, Montreal", 12345L, "05/27", 345); // Invalid credit card number

        when(repo.findPaymentByPaymentId(paymentId)).thenReturn(existingPayment);

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.updatePayment(paymentId, "555 Sherbrooke West, Montreal", 12345L, "05/27", 345);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Credit card number must be 16 digits", exception.getMessage());
        verify(repo, times(1)).findPaymentByPaymentId(paymentId);
    }

    @Test
    public void testUpdatePaymentWithExpiredExpirationDate() {
        // Arrange
        int paymentId = 1;
        Payment existingPayment = new Payment("555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 345);
        Payment payment = new Payment("555 Sherbrooke West, Montreal", 1111222233334444L, "04/20", 345);
        PaymentRequestDto paymentRequest = new PaymentRequestDto("555 Sherbrooke West, Montreal", 1111222233334444L, "04/20", 345); // Expired expiration date

        when(repo.findPaymentByPaymentId(paymentId)).thenReturn(existingPayment);

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.updatePayment(paymentId, "555 Sherbrooke West, Montreal", 1111222233334444L, "04/20", 345);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Expiration date must be in the future", exception.getMessage());
        verify(repo, times(1)).findPaymentByPaymentId(paymentId);
    }

    @Test
    public void testUpdatePaymentWithInvalidCVC() {
        // Arrange
        int paymentId = 1;
        Payment existingPayment = new Payment("555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 345);
        Payment payment = new Payment("555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 12);
        PaymentRequestDto paymentRequest = new PaymentRequestDto("555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 12); // Invalid CVC (only 2 digits)

        when(repo.findPaymentByPaymentId(paymentId)).thenReturn(existingPayment);

        // Act & Assert
        GameShopException exception = assertThrows(GameShopException.class, () -> {
            service.updatePayment(paymentId, "555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 12);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("CVC must be 3 digits", exception.getMessage());
        verify(repo, times(1)).findPaymentByPaymentId(paymentId);
    }


    @Test
    public void testDeletePaymentValidId() {
        // Arrange
        int paymentId = 1;
        Payment payment = new Payment("Address", 1111222233334444L, "04/27", 123);
        when(repo.findPaymentByPaymentId(paymentId)).thenReturn(payment);
        doNothing().when(repo).delete(payment);

        // Act
        service.deletePayment(paymentId);

        // Assert
        verify(repo, times(1)).findPaymentByPaymentId(paymentId);
        verify(repo, times(1)).delete(payment);
    }

    @Test
    public void testDeletePaymentInvalidId() {
        // Arrange
        int invalidPaymentId = 99;
        when(repo.findPaymentByPaymentId(invalidPaymentId)).thenReturn(null);

        // Act & Assert
        GameShopException e = assertThrows(GameShopException.class, () -> service.deletePayment(invalidPaymentId));
        assertEquals("Payment with ID " + invalidPaymentId + " does not exist.", e.getMessage());
        verify(repo, times(1)).findPaymentByPaymentId(invalidPaymentId);
        verify(repo, times(0)).delete(any(Payment.class));
    }

    @Test
    public void testGetAllPayments() {
        // Arrange
        Payment payment1 = new Payment("Address 1", 1111222233334444L, "05/28", 123);
        Payment payment2 = new Payment("Address 2", 5555666677778888L, "06/29", 456);
        List<Payment> payments = Arrays.asList(payment1, payment2);
        when(repo.findAll()).thenReturn(payments);

        // Act
        List<Payment> allPayments = (List<Payment>)service.getAllPayments();

        // Assert
        assertNotNull(allPayments);
        assertEquals(2, allPayments.size());
        assertEquals(payment1, allPayments.get(0));
        assertEquals(payment2, allPayments.get(1));
        verify(repo, times(1)).findAll();
    }
}
