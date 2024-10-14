package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Wishlist;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {
    public Wishlist findWishlistByWishlistId(int wishlistId);
}


