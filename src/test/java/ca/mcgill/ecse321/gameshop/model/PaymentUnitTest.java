package ca.mcgill.ecse321.gameshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.gameshop.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

@SpringBootTest
public class PaymentUnitTest {
    @Mock
    private PaymentRepository repo;
    @InjectMocks
    private PaymentService service;

    @SuppressWarnings("null")
    @Test

    public void testCreateValidPayment() {
        //Arrange
        String billingAddress = "555 Sherbrooke West, Montreal";
        long creditCardNb = 1111222233334444L; //had to change to long
        String exp = "04/27";
        int cvc = 345;
        double total = 45.66; //have to change to a double
        Payment payment = new Payment(billingAddress, creditCardNb, exp, cvc, total);

        //Act
        Payment createdPayment = service.createPayment(billingAddress, creditCardNb, exp, cvc, total);

        //Assert
        assertNotNull(createdPayment);
        assertEquals(billingAddress, createdPayment.getBillingAddress());
        assertEquals(creditCardNb, createdPayment.getCreditCardNb());
        assertEquals(exp, createdPayment.getExpirationDate());
        assertEquals(cvc, createdPayment.getCvc());
        assertEquals(total, createdPayment.getTotal());
        verify(repo, times(1)).save(payment);
    }

    @Test
    public void testReadPaymentByValidId() {
        //Arrange
        int id = 3;
        Payment payment = new Payment("555 Sherbrooke West, Montreal", 1111222233334444L, "04/27", 345, 45.66 );
        when(repo.findPaymentByPaymentId(id)).thenReturn(payment);

        //Act
        Payment paymentToGet = service.getPaymentById(id);

        //Assert
        assertNotNull(paymentToGet);
        assertEquals(payment.getBillingAddress(), paymentToGet.getBillingAddress());
        assertEquals(payment.getCreditCardNb(), paymentToGet.getCreditCardNb());
        assertEquals(payment.getExpirationDate(), paymentToGet.getExpirationDate());
        assertEquals(payment.getCvc(), paymentToGet.getCvc());
        assertEquals(payment.getTotal(), paymentToGet.getTotal());
    }

    @Test
    public void testReadPaymentByInvalidId() {
        //Arrange
        int id = 3;
        when(repo.findPaymentByPaymentId(id)).thenReturn(null);

        //Act
        //Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->service.getPaymentById(id));
        assertEquals("There is no person with ID " + id + ".", e.getMessage());
        verify(repo, times(1)).findPaymentByPaymentId(id);
    }

    @Test
    public void testUpdatePaymentValidId() {
        // Arrange
        int paymentId = 1;
        Payment existingPayment = new Payment("Old Address", 1111222233334444, "04/27", 123, 100);
        Payment updatedDetails = new Payment("New Address", 5555666677778888L, "12/30", 456, 200);
        when(repo.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        when(repo.save(any(Payment.class))).thenReturn(updatedDetails);

        // Act
        Payment updatedPayment = service.updatePayment(paymentId, updatedDetails);

        // Assert
        assertNotNull(updatedPayment);
        assertEquals(updatedDetails.getBillingAddress(), updatedPayment.getBillingAddress());
        assertEquals(updatedDetails.getCreditCardNb(), updatedPayment.getCreditCardNb());
        assertEquals(updatedDetails.getExpirationDate(), updatedPayment.getExpirationDate());
        assertEquals(updatedDetails.getCvc(), updatedPayment.getCvc());
        assertEquals(updatedDetails.getTotal(), updatedPayment.getTotal());
        verify(repo, times(1)).findById(paymentId);
        verify(repo, times(1)).save(any(Payment.class));
    }

    @Test
    public void testUpdatePaymentInvalidId() {
        // Arrange
        int invalidPaymentId = 99;
        Payment updatedDetails = new Payment("New Address", 5555666677778888L, "12/30", 456, 200);

        // Mock findById to return an empty Optional, simulating a non-existent payment
        when(repo.findById(invalidPaymentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.updatePayment(invalidPaymentId, updatedDetails)
        );
        assertEquals("Review not found", exception.getMessage());

        verify(repo, times(1)).findById(invalidPaymentId);
        verify(repo, times(0)).save(any(Payment.class)); // Ensure save is not called
    }

    @Test
    public void testDeletePayment() {
        // Arrange
        int paymentId = 1;
        doNothing().when(repo).deleteById(paymentId);

        // Act
        service.deletePayment(paymentId);

        // Assert
        verify(repo, times(1)).deleteById(paymentId);
    }

    @Test
    public void testGetAllPayments() {
        // Arrange
        Payment payment1 = new Payment("Address 1", 1111222233334444L, "05/28", 123, 100);
        Payment payment2 = new Payment("Address 2", 5555666677778888L, "06/29", 456, 200);
        List<Payment> payments = Arrays.asList(payment1, payment2);
        when(repo.findAll()).thenReturn(payments);

        // Act
        List<Payment> allPayments = service.getAllPayments();

        // Assert
        assertNotNull(allPayments);
        assertEquals(2, allPayments.size());
        assertEquals(payment1, allPayments.get(0));
        assertEquals(payment2, allPayments.get(1));
        verify(repo, times(1)).findAll();
    }
}
