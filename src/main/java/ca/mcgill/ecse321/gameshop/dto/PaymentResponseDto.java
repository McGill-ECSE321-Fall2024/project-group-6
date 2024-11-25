package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Payment;

/**
 * @author Annabelle Huynh-Rondeau
 */
public class PaymentResponseDto {
    // Payment attributes
    private String billingAddress;
    private int paymentId;

    public PaymentResponseDto() {
    } //default constructor

    public PaymentResponseDto(Payment payment) {
        this.billingAddress = payment.getBillingAddress();
        this.paymentId = payment.getPaymentId();
    }

    //GETTERS

    public String getBillingAddress() {
        return billingAddress;
    }

    public int getPaymentId() {
        return paymentId;
    }
}

