package ca.mcgill.ecse321.gameshop.repository;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.model.Payment;

@SpringBootTest
public class PaymentPersistenceTest {


    // private WishlistRepository wishRepo;

    @Autowired
    private PaymentRepository paymentRepo;


    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        paymentRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadPayment() {


        Payment payment = new Payment("1234 Montreal",123456789,"05/27",444);
        payment = paymentRepo.save(payment);
        Payment paymentFromDB = paymentRepo.findPaymentByPaymentId(payment.getPaymentId());
        assertNotNull(paymentFromDB);
        assertEquals(paymentFromDB.getCvc(),444);

    }
}
