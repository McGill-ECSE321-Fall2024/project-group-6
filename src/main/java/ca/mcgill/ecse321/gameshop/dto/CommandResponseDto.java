package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.model.Customer;

public class CommandResponseDto {

    private int commandId;
    public String commandDate;
    public float totalPrice;
    private Customer customer;

    // Jackson needs a default constructor, but it doesn't need to be public
    public CommandResponseDto() {
    }

    // Constructor
    public CommandResponseDto(Command c) {
        this.commandId = c.getCommandId();
        this.commandDate = c.getCommandDate();
        this.totalPrice = c.getTotalPrice();
        this.customer=c.getCustomer();
        System.out.println("response: "+ c.getTotalPrice());
    }

    // Getters
    public int getCommandId() {
        return commandId;
    }

    public String getCommandDate() {
        return commandDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
    public Customer getCustomer(){
        return customer;
    }
}