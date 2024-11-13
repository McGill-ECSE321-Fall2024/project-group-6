package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    // Inject CustomerRepository to handle database operations
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private GameRepository gameRepo;



    // Create new customer and save it in the repository
    @Transactional
    public Customer createCustomer(Person aPerson, String aShippingAddress) {
        Customer c = new Customer(aPerson, aShippingAddress);

        if(c.getPerson().getPassword().length()<10){
            throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
        }
        if(c.getPerson().getEmail()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
        }

        if(c.getPerson().getPhone()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
        }
        if(c.getPerson().getUsername()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
        }
        if(c.getShippingAddress()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Shipping address can not be null"));
        }
        personRepo.save(aPerson);
        return customerRepo.save(c);
    }

    // Retrieve all customers from repository
    public Iterable<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Find a customer by their ID
    @Transactional
    public Customer getCustomerByID(int id) {
        Customer c = customerRepo.findCustomerByRoleId(id);

        // Exception if no customer is found
        if (c == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        return c;
    }

    // Update an existing customer by their ID
    @Transactional
    public Customer updateCustomer(int id, String aUsername, String aEmail, String aPassword, String aPhone, String aShippingAddress) {
        Customer c = customerRepo.findCustomerByRoleId(id);


        if (c == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

            if (aPassword.length() < 10 && !aPassword.equals("disabled")) {
                throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
            }
            if (aEmail == null) {
                throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
            }

            if (aPhone == null) {
                throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
            }
            if (aUsername == null) {
                throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
            }
            if (aShippingAddress == null) {
                throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Shipping address can not be null"));
            }

        c.getPerson().setPhone(aPhone);
        c.getPerson().setEmail(aEmail);
        c.getPerson().setUsername(aUsername);
        c.getPerson().setPhone(aPhone);

        c.setShippingAddress(aShippingAddress);

        return customerRepo.save(c);
    }

    // Delete a customer by their ID
    @Transactional
    public void deleteCustomer(int id) {
        Customer c = customerRepo.findCustomerByRoleId(id);

        if (c == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        customerRepo.delete(c);
    }
    @Transactional
    public Customer addGameToCustomerCart(int id, Game game) {
        Customer customerFromDB= customerRepo.findCustomerByRoleId(id);
        if (customerFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }
        if(game==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Game can not be null"));
        }
        List<Game> gamesCart= customerFromDB.getCart();

        if(customerFromDB.getCart()==null){
            gamesCart=  new ArrayList<>();

        }

        gamesCart.add(game);
        customerFromDB.setCart(gamesCart);
        return customerRepo.save(customerFromDB);
    }
    @Transactional
    public Customer deleteGameFromCustomerCart(int id,Game game) {
        Customer customerFromDB= customerRepo.findCustomerByRoleId(id);
        if (customerFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }
        if(game==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Game can not be null"));
        }
        List<Game> gamesCart = customerFromDB.getCart();
        if (gamesCart == null) {
            gamesCart = new ArrayList<>();
        }

        gamesCart.remove(game);
        customerFromDB.setCart(gamesCart);

        return customerRepo.save(customerFromDB);
    }
    @Transactional
    public Customer addGameToCustomerWishList(int id, Game game) {
        Customer customerFromDB= customerRepo.findCustomerByRoleId(id);
        if (customerFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }
        if(game==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Game can not be null"));
        }
        List<Game> gamesWishlist= customerFromDB.getWishlist();
        if(customerFromDB.getWishlist()==null){
            gamesWishlist=  new ArrayList<>();
        }
        gamesWishlist.add(game);
        //gameRepo.save(game);
        customerFromDB.setWishlist(gamesWishlist);

        return customerRepo.save(customerFromDB);
    }
    @Transactional
    public Customer deleteGameFromCustomerWishList(int id,Game game) {
        Customer customerFromDB= customerRepo.findCustomerByRoleId(id);
        if (customerFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }
        if(game==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Game can not be null"));
        }
        List<Game> gamesWishlist = customerFromDB.getWishlist();
        if (gamesWishlist == null) {
            gamesWishlist = new ArrayList<>();
        }

        gamesWishlist.remove(game);


        customerFromDB.setWishlist(gamesWishlist);

        return customerRepo.save(customerFromDB);
    }
    @Transactional
    public List<Game> getCustomerCart(int id) {
        Customer customerFromDB= customerRepo.findCustomerByRoleId(id);
        if (customerFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        return customerFromDB.getCart();
    }
    @Transactional
    public List<Game> getCustomerWishlist(int id) {
        Customer customerFromDB= customerRepo.findCustomerByRoleId(id);
        if (customerFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        return customerFromDB.getWishlist();
    }

    /**
     * This was added for integration testing purposes, nothing else
     * @param aName
     * @param aDescription
     * @param aPrice
     * @param aStockQuantity
     * @param aPhotoURL
     * @return
     */
    @Transactional
    public Game addGame(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL) {

        Game game= new Game(aName,aDescription,aPrice,aStockQuantity,aPhotoURL);
        return gameRepo.save(game);
    }
}
