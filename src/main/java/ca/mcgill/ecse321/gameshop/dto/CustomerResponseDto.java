package ca.mcgill.ecse321.gameshop.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;

public class CustomerResponseDto {
    private String shippingAddress;
    private String username;
    private String email;
    private String phone;
    private int customerId;
    private List<Game> wishlist=new ArrayList<>();

    private List<Game> cart=new ArrayList<>();

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
        this.cart=customer.getCart();
        this.wishlist=customer.getWishlist();
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
    public List<Game> getCart() {
        return cart;
    }
    public List <Game> getWishlist() {
        return wishlist;
    }
}
