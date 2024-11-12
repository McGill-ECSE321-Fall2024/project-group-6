package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.model.Payment;

public class CommandResponseDto {
    //Command attributes
    private int id;
    private String commandDate;
    private float total;
    private Payment payment;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private CommandResponseDto() {
    }

    public CommandResponseDto(Command command){
        this.id = command.getCommandId();
        this.commandDate = command.getCommandDate();
        this.total = command.getTotalPrice();
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

    public Payment getPayment(){
        return payment;
    }
}
