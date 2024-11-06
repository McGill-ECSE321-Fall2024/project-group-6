package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class PaymentListDto {

    private List<PaymentResponseDto> payments;

    public PaymentListDto(List<PaymentResponseDto> payments) {
        this.payments = payments;
    }

    public List<PaymentResponseDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResponseDto> payments) {
        this.payments = payments;
    }
}
