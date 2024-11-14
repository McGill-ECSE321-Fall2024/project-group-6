package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Command;

public class CommandResponseDto {

    private int commandId;
    private String commandDate;
    private float totalPrice;

    // Jackson needs a default constructor, but it doesn't need to be public
    public CommandResponseDto() {
    }

    // Constructor
    public CommandResponseDto(Command c) {
        this.commandId = c.getCommandId();
        this.commandDate = c.getCommandDate();
        this.totalPrice = c.getTotalPrice();
    }

    // Getters
    public int getCommandId() {
        return commandId;
    }

    public String getCommandDate() {
        return commandDate;
    }

    public float getTotal() {
        return totalPrice;
    }
}
