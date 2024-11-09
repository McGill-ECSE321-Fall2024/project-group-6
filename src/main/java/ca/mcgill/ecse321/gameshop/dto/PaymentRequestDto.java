package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Payment;

public class PaymentRequestDto {

    private String billingAddress;
    private long creditCardNb;
    private String expirationDate;
    private int cvc;
    //private int customerId;
    //private int commandId;
    //no payment id for request dto

    public PaymentRequestDto() {
    }

    public PaymentRequestDto(Payment payment) {
        this.billingAddress = payment.getBillingAddress();
        this.creditCardNb = payment.getCreditCardNb();
        this.expirationDate = payment.getExpirationDate();
        this.cvc = payment.getCvc();
        //this.customerId = payment.getCustomer().getRoleId();
        //this.commandId = payment.getCommand().getCommandId();
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

   public long getCreditCardNb() {
        return creditCardNb;
    }

    public void setCreditCardNb(int creditCardNb) {
        this.creditCardNb = creditCardNb;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    //public int getCustomerId() {
        //return customerId;
    //}

    //public void setCustomerId(int customerId) {
        //this.customerId = customerId;
    //}

    //public int getCommandId() {
        //return commandId;
    //}

    //public void setCommandId(int commandId) {
        //this.commandId = commandId;
    //}
}


