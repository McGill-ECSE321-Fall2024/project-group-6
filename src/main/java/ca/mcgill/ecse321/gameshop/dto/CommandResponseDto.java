package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Command;

public class CommandResponseDto {
    private int id;
    private String commandDate;
    private float total;

    public CommandResponseDto(Command c){
        this.id=c.getCommandId();
        this.commandDate=c.getCommandDate();
        this.total=c.getTotalPrice();
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
}
