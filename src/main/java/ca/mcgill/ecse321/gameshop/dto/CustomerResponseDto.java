package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;

public class CustomerResponseDto {
    private int customerId;
    private List<Game> wishlistGames;
    private List<Game> cartGames;

    @SuppressWarnings("unused") // default constructor is needed but does not need to be public

    public CustomerResponseDto(Customer customer) {
        this.customerId = customer.getRoleId();
        this.wishlistGames = customer.getWishlist().getGames();
        this.cartGames = customer.getCart().getGames();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int id) {
        this.customerId = id;
    }

    public List<Game> getWishlistGames() {
        return wishlistGames;
    }

    public void setWishlistGames(List<Game> wishlistGames) {
        this.wishlistGames = wishlistGames;
    }

    public List<Game> getCartGames() {
        return cartGames;
    }

    public void setCartGames(List<Game> cartGames) {
        this.cartGames = cartGames;
    }

}
