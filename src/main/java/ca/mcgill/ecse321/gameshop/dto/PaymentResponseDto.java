package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Payment;

public class PaymentResponseDto {
    // Payment attributes
    private String billingAddress;
    private int paymentId;

    public PaymentResponseDto() {
    }

    public PaymentResponseDto(Payment payment) {
        this.billingAddress = payment.getBillingAddress();
        this.paymentId = payment.getPaymentId();
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public int getPaymentId() {
        return paymentId;
    }
}

