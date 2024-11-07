package ca.mcgill.ecse321.gameshop.dto;

import jakarta.validation.constraints.NotBlank;

public class PaymentRequestDto {
    //Payment attributes
    @NotBlank(message="Billing Address is required.")
    private String billingAddress;
    @NotBlank(message="Credit card number is required.")
    private long creditCardNumber;
    @NotBlank(message="Expiration date is required.")
    private String expirationDate;
    @NotBlank(message="CVC is required.")
    private int cvc;

    public PaymentRequestDto() {
    }

    public PaymentRequestDto(String billingAddress, long creditCardNumber, String expirationDate, int cvc) {
        this.billingAddress = billingAddress;
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public int getCvc() {
        return cvc;
    }
}


