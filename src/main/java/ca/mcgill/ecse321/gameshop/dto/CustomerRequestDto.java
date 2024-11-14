package ca.mcgill.ecse321.gameshop.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Game;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequestDto {
    @NotBlank(message="Customer address is required.")
    private String shippingAddress;
    @NotBlank(message="Person name is required.")
    private String username;
    @NotBlank(message = "Email address is required.")
	@Email(message = "Invalid email address.")
    private String email;
    @NotBlank(message="A phone number is required.")
    private String phone;
    @NotBlank(message="A password is required.")
    private String password;


    private List<Game> wishlist=new ArrayList<>();

    private List<Game> cart=new ArrayList<>();

    @SuppressWarnings("unused")
    private CustomerRequestDto() {
    }

    public CustomerRequestDto(String shippingAddress, String username, String email, String phone, String password,List<Game> cart,List <Game> wishlist) {
        this.shippingAddress = shippingAddress;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.cart=cart;
        this.wishlist=wishlist;
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

    public String getPassword() {
        return password;
    }
    public List <Game> getCart() {
        return cart;
    }
    public List <Game> getWishlist() {
        return wishlist;
    }
}
