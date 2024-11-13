package ca.mcgill.ecse321.gameshop.controller;


import ca.mcgill.ecse321.gameshop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;

import ca.mcgill.ecse321.gameshop.model.*;
import java.util.*;
import ca.mcgill.ecse321.gameshop.service.*;

@RestController
public class CustomerController {


    @Autowired
    private CustomerService customerService;
    @Autowired
    private GameService gameService;



    @PostMapping("/customers")
    public CustomerResponseDto createCustomer(@Valid @RequestBody CustomerRequestDto customerToCreate) {
    Person person= new Person(customerToCreate.getUsername(),customerToCreate.getEmail(),customerToCreate.getPassword(),customerToCreate.getPhone());
        Customer customer = customerService.createCustomer(person,customerToCreate.getShippingAddress());

        return new CustomerResponseDto(customer);
    }

    /**
     * Only for testing purposes
     * @return
     */
    @PostMapping("/customers/test")
    public GameResponseDto createGameForCustomerTesting(@RequestBody GameRequestDto2 g){
        Game game = customerService.addGame(g.getName(), g.getDescription(), g.getPrice(), g.getStockQuantity(), g.getPhotoURL());
        return new GameResponseDto(game);
    }



    @GetMapping("/customers/{cid}")
    public CustomerResponseDto findCustomerById(@PathVariable int cid) {
        Customer customer=customerService.getCustomerByID(cid);

        return new CustomerResponseDto(customer);
    }
    @GetMapping("/customers")
    public CustomerListDto getAllCustomers() {
        List<CustomerResponseDto> customers = new ArrayList<>();
        for (Customer c: customerService.getAllCustomers()) {
            customers.add(new CustomerResponseDto(c));
        }
        return new CustomerListDto(customers);
    }


    @DeleteMapping("/customers/{cid}")
    public void deleteCustomer(@PathVariable int cid) {
        customerService.deleteCustomer(cid);

    }


    @PutMapping("/customers/{cid}")
    public CustomerResponseDto updateCustomer(@PathVariable int cid, @RequestBody CustomerRequestDto customer) {
       Customer c = customerService.updateCustomer(cid, customer.getUsername(),customer.getEmail(),customer.getPassword(),customer.getPhone(),customer.getShippingAddress());

        return new CustomerResponseDto(c);
    }
    @PutMapping("/customers/{cid}/cart")
    public CustomerResponseDto addGameToCart(@PathVariable int cid, @RequestBody String name) {

            Game g = gameService.getGameByName(name);
            Customer c = customerService.addGameToCustomerCart(cid, g);
            return new CustomerResponseDto(c);
        }


/*
    @GetMapping("/customers/{cid}/cart")
    public List <Game> getAllGamesInCart(@PathVariable int cid) {

        return customerService.getCustomerCart(cid);
    }

 */
    @PutMapping("/customers/{cid}/cart/game")
    public CustomerResponseDto DeleteGameFromCart(@PathVariable int cid, @RequestBody String name) {
        Game g = gameService.getGameByName(name);
        Customer c = customerService.deleteGameFromCustomerCart(cid, g);
        return new CustomerResponseDto(c);
    }
    @PutMapping("/customers/{cid}/wishlist")
    public CustomerResponseDto addGameToWishlist(@PathVariable int cid, @RequestBody String name) {
        Game g = gameService.getGameByName(name);
        Customer c = customerService.addGameToCustomerWishList(cid, g);
        return new CustomerResponseDto(c);
    }
/*
    @GetMapping("/customers/{cid}/wishlist")
    public List <Game> getAllGamesInWishlist(@PathVariable int cid) {

        return customerService.getCustomerWishlist(cid);
    }

 */
    @PutMapping("/customers/{cid}/wishlist/game")
    public CustomerResponseDto DeleteGameFromWishlist(@PathVariable int cid, @RequestBody String name) {
        Game g = gameService.getGameByName(name);
        Customer c = customerService.deleteGameFromCustomerWishList(cid, g);
        return new CustomerResponseDto(c);
    }


}
