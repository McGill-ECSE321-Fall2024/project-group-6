package ca.mcgill.ecse321.gameshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CommandRequestDto {
    @Positive(message = "Total price must be greater than 0.")
    private float totalPrice;
    @NotBlank(message = "Command date must not be blank.")
    private String commandDate;

    // Jackson needs a default constructor, but it doesn't need to be public
    public CommandRequestDto() {
    }

    // Constructor
    public CommandRequestDto(float totalPrice, String commandDate) {
        this.totalPrice = totalPrice;
        this.commandDate = commandDate;
    }

    public float getTotal() {
        return totalPrice;
    }

    public String getCommandDate() {
        return commandDate;
    }
}