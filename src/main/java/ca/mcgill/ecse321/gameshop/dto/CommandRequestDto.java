package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Payment;
import jakarta.validation.constraints.NotBlank;

public class CommandRequestDto {
    //Command attributes
    @NotBlank(message="Command cost is required.")
    private float total;
    private Payment payment;

    @SuppressWarnings("unused")
    private CommandRequestDto() {
    }

    public CommandRequestDto(float total) {
        this.total = total;
    }

    public float getTotal(){
        return total;
    }

    public Payment getPayment(){
        return payment;
    }
}
