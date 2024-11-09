package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.dto.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class PaymentService {
    // Inject PaymentRepository to handle database operations
    @Autowired
    private PaymentRepository paymentRepo;

    // Create a new payment and save it in the repository
    @Transactional
    public Payment createPayment(String aBillingAddress, long aCreditCardNb, String aExpirationDate, int aCvc) {
        // check that billing address is valid
        if (aBillingAddress == null || aBillingAddress.trim().isEmpty()) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Billing address cannot be empty");
        }

        // check that the credit card number has 16 digits
        String creditCardString = String.valueOf(aCreditCardNb);
        if (creditCardString.length() != 16) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Credit card number must be 16 digits");
        }

        // check the format of the expiration date (MM/YY )
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            LocalDate expiryDate = LocalDate.parse("01/" + aExpirationDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
            if (!expiryDate.isAfter(LocalDate.now())) {
                throw new GameShopException(HttpStatus.BAD_REQUEST, "Expiration date must be in the future");
            }
        } catch (DateTimeParseException e) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Invalid expiration date format. Use MM/YY");
        }

        // check if CVC is in the right format (3 digits)
        String cvcString = String.valueOf(aCvc);
        if (cvcString.length() != 3) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "CVC must be 3 digits");
        }

        //if all inputs are correct, create the new payment
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

        // check that billing address is valid
        String aBillingAddress = paymentDetails.getBillingAddress();
        if (aBillingAddress == null || aBillingAddress.trim().isEmpty()) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Billing address cannot be empty");
        }

        // check that the credit card number has 16 digits
        String creditCardString = String.valueOf(paymentDetails.getCreditCardNb());
        if (creditCardString.length() != 16) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Credit card number must be 16 digits");
        }

        // check the format of the expiration date (MM/YY )
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            LocalDate expiryDate = LocalDate.parse("01/" + paymentDetails.getExpirationDate(), DateTimeFormatter.ofPattern("dd/MM/yy"));
            if (!expiryDate.isAfter(LocalDate.now())) {
                throw new GameShopException(HttpStatus.BAD_REQUEST, "Expiration date must be in the future");
            }
        } catch (DateTimeParseException e) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Invalid expiration date format. Use MM/YY");
        }

        // check if CVC is in the right format (3 digits)
        String cvcString = String.valueOf(paymentDetails.getCvc());
        if (cvcString.length() != 3) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "CVC must be 3 digits");
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
