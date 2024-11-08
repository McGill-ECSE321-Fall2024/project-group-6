package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.dto.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
import jakarta.transaction.Transactional;

@Service
public class PaymentService {
    // Inject PaymentRepository to handle database operations
    @Autowired
    private PaymentRepository paymentRepo;

    // Create a new payment and save it in the repository
    @Transactional
    public Payment createPayment(String aBillingAddress, long aCreditCardNb, String aExpirationDate, int aCvc) {
        Payment p = new Payment(aBillingAddress, aCreditCardNb, aExpirationDate, aCvc);
        return paymentRepo.save(p);
    }

    // Retrieve all payments from the repository
    public Iterable<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    // Find a payment by its id
    public Payment getPaymentById(int id) {
        Payment p = paymentRepo.findPaymentByPaymentId(id);

        // Throw an exception if no payment is found
        if (p == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Payment with ID " + id + " does not exist."));
        }

        return p;
    }

    // Update an existing payment by ID
    @Transactional
    public Payment updatePayment(int id, PaymentRequestDto paymentDetails) {
        Payment p = paymentRepo.findPaymentByPaymentId(id);

        if (p == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Payment with ID " + id + " does not exist."));
        }

        p.setBillingAddress(paymentDetails.getBillingAddress());
        p.setCreditCardNb(paymentDetails.getCreditCardNb());
        p.setExpirationDate(paymentDetails.getExpirationDate());
        p.setCvc(paymentDetails.getCvc());
        
        return paymentRepo.save(p);
    }

    // Delete a person by their id
    public void deletePayment(int id) {
        Payment p = paymentRepo.findPaymentByPaymentId(id);

        if (p == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Payment with ID " + id + " does not exist."));
        }

        paymentRepo.delete(p);
    }

}
