package ca.mcgill.ecse321.gameshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CommandRequestDto {
    @Positive(message = "Total price must be greater than 0.")
    private float totalPrice;
    @NotBlank(message = "Command date must not be blank.")
    private String commandDate;
    private int customerID;
    // Jackson needs a default constructor, but it doesn't need to be public


    private CommandRequestDto(){}
    public CommandRequestDto(int cId){
        System.out.println("Request: ");
        this.customerID=cId;
    }

    public float getTotal() {
        return totalPrice;
    }

    public String getCommandDate() {
        return commandDate;
    }
    public int getCustomerID(){
        return customerID;
    }
}