package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class CustomerListDto {
    private List<CustomerResponseDto> customers;
    public CustomerListDto() {

    }
    public CustomerListDto(List<CustomerResponseDto> customers) {
        this.customers = customers;
    }

    public List<CustomerResponseDto> getCustomers() {
        return customers;
    }
}