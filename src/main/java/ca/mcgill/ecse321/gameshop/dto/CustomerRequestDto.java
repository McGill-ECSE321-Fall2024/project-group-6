package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequestDto {
    @NotBlank(message = "Shipping Address needs to be exist")
    private String shippingAddress;
    @NotBlank(message = "Person needs to be created")
    PersonRequestDto person;
    public CustomerRequestDto(PersonRequestDto aPerson,String aShippingAddress) {
        this.person=aPerson;
        this.shippingAddress=aShippingAddress;
    }

    public PersonRequestDto getPerson() {

        return person;
    }

    public String getShippingAddress() {

        return shippingAddress;
    }

    public void setPerson(PersonRequestDto person) {

        this.person=person;
    }
    public void setShippingAddress(String aShippingAddress) {

        this.shippingAddress=aShippingAddress;
    }


}