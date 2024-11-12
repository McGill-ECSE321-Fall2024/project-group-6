package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Payment;

public class CommandRequestDto {

    public float total;
    private Payment payment;
    public Customer customer;

    private CommandRequestDto(){}
    public CommandRequestDto(Customer c){
        this.customer=c;
    }
    public float getTotal(){
        return total;
    }

    public Payment getPayment(){
        return payment;
    }
    public Customer getCustomer(){
        return customer;
    }
    public void setCustomer(Customer c){
        this.customer=c;
    }

    public void setPayment(Payment p){
        this.payment=p;
    }

    public void setTotal(float t){
        this.total=t;
    }


}
