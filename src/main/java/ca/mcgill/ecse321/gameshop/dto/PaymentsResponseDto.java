package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class PaymentsResponseDto {

    private List<PaymentResponseDto> payments;

    public PaymentsResponseDto(List<PaymentResponseDto> payments) {
        this.payments = payments;
    }

    public List<PaymentResponseDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResponseDto> payments) {
        this.payments = payments;
    }
}
