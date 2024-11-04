package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;

public class CustomerRequestDto {

    private List<Game> wishlistGames;
    private List<Game> cartGames;

    public CustomerRequestDto(Customer customer) {
        this.wishlistGames = customer.getWishlist().getGames();
        this.cartGames = customer.getCart().getGames();
    }

    public List<Game> getCartGames() {
        return cartGames;
    }

    public void setCartGames(List<Game> cartGames) {
        this.cartGames = cartGames;
    }

    public List<Game> getWishlistGames() {
        return wishlistGames;
    }

    public void setWishlistGames(List<Game> wishlistGames) {
        this.wishlistGames = wishlistGames;
    }

}
