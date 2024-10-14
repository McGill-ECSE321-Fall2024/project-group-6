package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    public Cart findCartByCartId(int cartId);
}

