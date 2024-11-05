package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.PaymentRequestDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentResponseDto;
import ca.mcgill.ecse321.gameshop.dto.PaymentsResponseDto;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Return the new payment
     *
     * @param payment The payment object.
     * @return The new payment object.
     */
    @PostMapping
    public PaymentResponseDto createPayment(@RequestBody PaymentRequestDto payment) {
        Payment createdPayment = paymentService.createPayment(payment.getBillingAddress(), payment.getCreditCardNb(), payment.getExpirationDate(), payment.getCvc(), payment.getTotal());
        return new PaymentResponseDto(createdPayment);
    }

    /**
     * Return the updated payment with the given ID.
     *
     * @param paymentId the ID of the review
     * @return the updated payment with the given ID.
     */
    @PutMapping("/{paymentId}")
    public PaymentResponseDto updatePayment(@PathVariable int paymentId, @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(paymentId, payment);
        return new PaymentResponseDto(updatedPayment);
    }

    /**
     *@param paymentId the id of the payment to delete.
     * @return The response DTO of the payment deletion.
     */
    @DeleteMapping("/{paymentId}")
    public void deletePayment(@PathVariable int paymentId) {
        paymentService.deletePayment(paymentId);
    }

    /**
     * Return the payment with the given ID.
     *
     * @param paymentId the ID of the review
     * @return the payment with the given ID.
     */
    @GetMapping("/{paymentId}")
    public PaymentResponseDto getPaymentById(@PathVariable int paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        return new PaymentResponseDto(payment);
    }

    /**
     * Return all payments in the DB.
     *
     * @return Return all payments.
     */
    @GetMapping
    public PaymentsResponseDto getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        List<PaymentResponseDto> responseList = new ArrayList<>();
        for (Payment p : payments){
            PaymentResponseDto response = new PaymentResponseDto(p);
            responseList.add(response);
        }
        return new PaymentsResponseDto(responseList);
    }

}
