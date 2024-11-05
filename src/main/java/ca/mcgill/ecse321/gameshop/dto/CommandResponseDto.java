package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Command;

public class CommandResponseDto {
    //Command attributes
    private int id;
    private String commandDate;
    private float total;

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

    public void setTotal(float total){
        this.total = total;
    }
}