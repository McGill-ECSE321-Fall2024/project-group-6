package ca.mcgill.ecse321.gameshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.dto.PaymentListDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentRequestDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentResponseDto;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.service.PaymentService;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    /**
     * Return the new payment
     * @author Annabelle Huynh-Rondeau
     * @param payment The payment object.
     * @return The new payment object.
     */
    @PostMapping("/payment")
    public PaymentResponseDto createPayment(@RequestBody PaymentRequestDto payment) {
        Payment p = paymentService.createPayment(payment.getBillingAddress(), payment.getCreditCardNumber(), payment.getExpirationDate(), payment.getCvc());
        return new PaymentResponseDto(p);
    }

    /**
     * Return all payments in the DB.
     * @author Annabelle Huynh-Rondeau
     * @return Return all payments.
     */
    @GetMapping("/payment")
    public PaymentListDto getAllPayments() {
        List<PaymentResponseDto> payments = new ArrayList<>();

        for (Payment p: paymentService.getAllPayments()) {
            payments.add(new PaymentResponseDto(p));
        }

        return new PaymentListDto(payments);
    }

    /**
     * Return the payment with the given ID.
     *
     * @param id the ID of the review
     * @return the payment with the given ID.
     */
    @GetMapping("/payment/{id}")
    public PaymentResponseDto getPaymentById(@PathVariable int id) {
        Payment p = paymentService.getPaymentById(id);
        return new PaymentResponseDto(p);
    }

    /**
     * Update the payment with the given ID.
     *
     * @param id the ID of the review
     * @return the updated payment with the given ID.
     */
    @PutMapping("/payment/{id}")
    public PaymentResponseDto updatePayment(@PathVariable int id, @RequestBody PaymentRequestDto payment) {
        Payment p = paymentService.updatePayment(id, payment.getBillingAddress(), payment.getCreditCardNumber(), payment.getExpirationDate(), payment.getCvc());
        return new PaymentResponseDto(p);
    }

    /**
     * Delete the payment with the given id.
     * @author Annabelle Huynh-Rondeau
     *@param "/payment/{Id}" the id of the payment to delete.
     * @return The response DTO of the payment deletion.
     */
    @DeleteMapping("/payment/{id}")
    public void deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
    }
}
