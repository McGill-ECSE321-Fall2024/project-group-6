package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Payment;

/**
 * @author Annabelle Huynh-Rondeau
 */
public class PaymentResponseDto {
    // Payment attributes
    private String billingAddress;
    private int paymentId;
    private Customer customer;

    public PaymentResponseDto() {
    } //default constructor

    public PaymentResponseDto(Payment payment) {
        this.billingAddress = payment.getBillingAddress();
        this.paymentId = payment.getPaymentId();
        this.customer=payment.getCustomer();

    }

    //GETTERS

    public String getBillingAddress() {
        return billingAddress;
    }

    public int getPaymentId() {
        return paymentId;
    }


}

