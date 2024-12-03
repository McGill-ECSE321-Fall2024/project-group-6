package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;

import java.util.ArrayList;
import java.util.List;

public class CustomerResponseDto {
    private String shippingAddress;
    private String username;
    private String email;
    private String phone;
    private int customerId;
    private String password;
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
        this.password=customer.getPerson().getPassword();
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
    public String getPassword() {
        return password;
    }
}