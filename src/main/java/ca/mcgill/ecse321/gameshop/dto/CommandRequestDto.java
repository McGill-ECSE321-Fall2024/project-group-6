package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Payment;

public class CommandRequestDto {
    private float total;
    private Payment payment;


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
