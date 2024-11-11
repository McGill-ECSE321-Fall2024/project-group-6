package ca.mcgill.ecse321.gameshop.dto;

public class PaymentRequestDto {
    //Payment attributes
    private String billingAddress;
    private long creditCardNumber;
    private String expirationDate;
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


