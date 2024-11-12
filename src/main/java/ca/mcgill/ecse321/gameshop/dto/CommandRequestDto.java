package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Payment;
import jakarta.validation.constraints.NotBlank;

public class CommandRequestDto {
    @NotBlank(message = "Total should be > 0.")
    private float total;
    private Payment payment;
    private Customer customer;

    @SuppressWarnings("unused")
    private CommandRequestDto(){
    }

    public CommandRequestDto(Customer c){
        this.customer=c;
    }

    public float getTotal(){
        return total;
    }

    public Payment getPayment(){
        return payment;
    }

    public Customer getCustomer(){
        return customer;
    }
}
