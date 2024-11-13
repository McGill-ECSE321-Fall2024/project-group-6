package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

/**
 * @author Annabelle Huynh-Rondeau
 */

public class PaymentListDto {
    //DTO for the getAll method
    private List<PaymentResponseDto> payments;

    public PaymentListDto(List<PaymentResponseDto> payments) {
        this.payments = payments;
    }

    public List<PaymentResponseDto> getPayments() {
        return payments;
    }
}
