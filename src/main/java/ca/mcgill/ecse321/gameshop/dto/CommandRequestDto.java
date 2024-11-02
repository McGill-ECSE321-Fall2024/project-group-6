package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Payment;

public class CommandRequestDto {
    private int id;
    private String commandDate;
    private float total;
    private Payment payment;

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
