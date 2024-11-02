package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.model.Payment;

public class CommandResponseDto {
    private int id;
    private String commandDate;
    private float total;
    private Payment payment;

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

    public Payment getPayment(){
        return payment;
    }

    public void setPayment(Payment p){
        this.payment=p;
    }

    public void setTotal(float t){
        this.total=t;
    }
}
