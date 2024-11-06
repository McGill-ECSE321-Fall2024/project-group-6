package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;

public class CustomerResponseDto {
    private String shippingAddress;
    PersonResponseDto person;


    public CustomerResponseDto(PersonResponseDto aPerson,String aShippingAddress) {
      this.person=aPerson;
      this.shippingAddress=aShippingAddress;
    }

    public PersonResponseDto getPerson() {
        return person;
    }

    public String getShippingAddress() {

        return shippingAddress;
    }

    public void setPerson(PersonResponseDto person) {

        this.person=person;
    }
    public void setShippingAddress(String aShippingAddress) {

        this.shippingAddress=aShippingAddress;
    }


}
