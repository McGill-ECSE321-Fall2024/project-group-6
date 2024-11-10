package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Customer;

public class CustomerResponseDto {
    private String shippingAddress;
    private String username;
    private String email;
    private String phone;
    private int customerId;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private CustomerResponseDto() {
    }

    public CustomerResponseDto(Customer customer) {
        this.customerId = customer.getRoleId();
        this.username = customer.getPerson().getUsername();
        this.email = customer.getPerson().getEmail();
        this.phone = customer.getPerson().getPhone();
        this.shippingAddress = customer.getShippingAddress();
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getCustomerId() {
        return customerId;
    }
}
