package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
import ca.mcgill.ecse321.gameshop.dto.PaymentListDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentRequestDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentResponseDto;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class PaymentService {

    // Inject PaymentRepository to handle database operations
    @Autowired
    private PaymentRepository paymentRepo;

    /**
     * Creates a new payment and saves it in the repository.
     *
     * @author Annabelle Huynh-Rondeau
     * @param aBillingAddress The billing address associated with the payment.
     * @param aCreditCardNb The credit card number associated with the payment.
     * @param aExpirationDate The expiration date of the credit card in MM/YY format.
     * @param aCvc The CVC code of the credit card.
     * @return The newly created Payment object.
     */
    @Transactional
    public Payment createPayment(String aBillingAddress, long aCreditCardNb, String aExpirationDate, int aCvc) {
        // Check that billing address is valid
        if (aBillingAddress == null || aBillingAddress.trim().isEmpty()) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Billing address cannot be empty");
        }

        // Check that the credit card number has 16 digits
        String creditCardString = String.valueOf(aCreditCardNb);
        if (creditCardString.length() != 16) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Credit card number must be 16 digits");
        }

        // Check the format of the expiration date (MM/YY)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            LocalDate expiryDate = LocalDate.parse("01/" + aExpirationDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
            if (!expiryDate.isAfter(LocalDate.now())) {
                throw new GameShopException(HttpStatus.BAD_REQUEST, "Expiration date must be in the future");
            }
        } catch (DateTimeParseException e) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Invalid expiration date format. Use MM/YY");
        }

        // Check if CVC is in the right format (3 digits)
        String cvcString = String.valueOf(aCvc);
        if (cvcString.length() != 3) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "CVC must be 3 digits");
        }

        // If all inputs are correct, create the new payment
        Payment p = new Payment(aBillingAddress, aCreditCardNb, aExpirationDate, aCvc);
        return paymentRepo.save(p);
    }

    /**
     * Retrieves all payments from the repository.
     *
     * @author Annabelle Huynh-Rondeau
     * @return An iterable collection of all Payment objects.
     */
    public Iterable<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    /**
     * Finds a payment by its ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @param id The ID of the payment to retrieve.
     * @return The Payment object corresponding to the given ID.
     * @throws GameShopException If no payment with the given ID is found.
     */
    public Payment getPaymentById(int id) {
        Payment p = paymentRepo.findPaymentByPaymentId(id);

        // Throw an exception if no payment is found
        if (p == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Payment with ID " + id + " does not exist."));
        }

        return p;
    }

    /**
     * Updates an existing payment by its ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @param id The ID of the payment to update.
     * @param aBillingAddress The updated billing address.
     * @param aCreditCardNb The updated credit card number.
     * @param aExpirationDate The updated expiration date (MM/YY format).
     * @param aCvc The updated CVC code.
     * @return The updated Payment object.
     * @throws GameShopException If no payment with the given ID is found.
     */
    @Transactional
    public Payment updatePayment(int id, String aBillingAddress, long aCreditCardNb, String aExpirationDate, int aCvc) {
        Payment p = paymentRepo.findPaymentByPaymentId(id);

        if (p == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Payment with ID " + id + " does not exist."));
        }

        // Check that billing address is valid
        if (aBillingAddress == null || aBillingAddress.trim().isEmpty()) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Billing address cannot be empty");
        }

        // Check that the credit card number has 16 digits
        String creditCardString = String.valueOf(aCreditCardNb);
        if (creditCardString.length() != 16) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Credit card number must be 16 digits");
        }

        // Check the format of the expiration date (MM/YY)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            LocalDate expiryDate = LocalDate.parse("01/" + aExpirationDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
            if (!expiryDate.isAfter(LocalDate.now())) {
                throw new GameShopException(HttpStatus.BAD_REQUEST, "Expiration date must be in the future");
            }
        } catch (DateTimeParseException e) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "Invalid expiration date format. Use MM/YY");
        }

        // Check if CVC is in the right format (3 digits)
        String cvcString = String.valueOf(aCvc);
        if (cvcString.length() != 3) {
            throw new GameShopException(HttpStatus.BAD_REQUEST, "CVC must be 3 digits");
        }

        // Update the payment details
        p.setBillingAddress(aBillingAddress);
        p.setCreditCardNb(aCreditCardNb);
        p.setExpirationDate(aExpirationDate);
        p.setCvc(aCvc);

        return paymentRepo.save(p);
    }

    /**
     * Deletes a payment by its ID.
     *
     * @author Annabelle Huynh-Rondeau
     * @param id The ID of the payment to delete.
     * @throws GameShopException If no payment with the given ID is found.
     */
    public void deletePayment(int id) {
        Payment p = paymentRepo.findPaymentByPaymentId(id);

        if (p == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Payment with ID " + id + " does not exist."));
        }

        paymentRepo.delete(p);
    }
}
