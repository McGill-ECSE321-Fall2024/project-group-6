package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.model.Customer;

public class CommandResponseDto {
    private int id;
    private String commandDate;
    private float total;
    private Customer customer;
    private CommandResponseDto(){}
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

    public void setTotal(float t){
        this.total=t;
    }
}
