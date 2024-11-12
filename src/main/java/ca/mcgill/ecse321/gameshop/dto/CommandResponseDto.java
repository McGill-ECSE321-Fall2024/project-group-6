package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.model.Customer;

public class CommandResponseDto {
    //Command attributes
    private int id;
    private String commandDate;
    private float total;
    private Customer customer;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private CommandResponseDto() {
    }
    
    public CommandResponseDto(Command c){
        this.id=c.getCommandId();
        this.commandDate=c.getCommandDate();
        this.total=c.getTotalPrice();
        this.customer=c.getCustomer();
    }

    public int getId(){
        return id;
    }

    public String getCommandDate(){
        return commandDate;
    }
    
    public float getTotal(){
        return total;
    }

    public Customer getCustomer() {
        return customer;
    }
}
