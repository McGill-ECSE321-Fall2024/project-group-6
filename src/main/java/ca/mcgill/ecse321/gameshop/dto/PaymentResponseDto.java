package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Payment;

public class PaymentResponseDto {

    private String billingAddress;
    private int total;
    private int paymentId;
    private int customerId;
    private int commandId;

    public PaymentResponseDto() {
    }

    public PaymentResponseDto(Payment payment) {
        this.billingAddress = payment.getBillingAddress();
        this.paymentId = payment.getPaymentId();
        this.customerId = payment.getCustomer().getRoleId();
        //this.commandId = payment.getCommand().getCommandId();
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    //public int getCommandId() {
        //return commandId;
    //}

    //public void setCommandId(int commandId) {
        //this.commandId = commandId;
    //}

}

