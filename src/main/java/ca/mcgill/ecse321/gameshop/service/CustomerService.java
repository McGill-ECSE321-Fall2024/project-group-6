package ca.mcgill.ecse321.gameshop.service;
/**
 * @author Joseph and Marine
 */

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private PaymentRepository paymentRepo;

    /**
     * Service method to create a customer
     * @param aPerson
     * @param aShippingAddress
     * @return
     */

    @Transactional
    public Customer createCustomer(Person aPerson, String aShippingAddress) {
        Customer c = new Customer(aPerson, aShippingAddress);
        Person customerPresenceCheck= personRepo.findPersonByEmail(aPerson.getEmail());
        if(customerPresenceCheck!=null){
            throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("You already have an account, please sign in"));
        }
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

    /**
     * Service method to get all customers
     * @return
     */
    public Iterable<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    /**
     * Service method to find customer by id
     * @param id
     * @return
     */
    @Transactional
    public Customer getCustomerByID(int id) {
        Customer c = customerRepo.findCustomerByRoleId(id);

        // Exception if no customer is found
        if (c == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        return c;
    }

    /**
     * Service method to update customer.
     * @param id
     * @param aUsername
     * @param aEmail
     * @param aPassword
     * @param aPhone
     * @param aShippingAddress
     * @return
     */
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

    /**
     * Service method to delete customer
     * @param id
     */
    @Transactional
    public void deleteCustomer(int id) {
        Customer c = customerRepo.findCustomerByRoleId(id);

        if (c == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        customerRepo.delete(c);
    }

    /**
     * Service method to add game to customer cart
     * @param id
     * @param game
     * @return
     */
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

    /**
     * Service method to remove game from customer cart
     * @param id
     * @param g
     * @return
     */
    @Transactional
    public Customer deleteGameFromCustomerCart(int id,Game g) {
        Customer customerFromDB= customerRepo.findCustomerByRoleId(id);
        if (customerFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }

        List<Game> gamesCartFromDb = customerFromDB.getCart();
        List<Game> gamesCart = new ArrayList<>();

        for (Game game : gamesCartFromDb) {
            if (game.getName() != g.getName()) {
                gamesCart.add(game);
            }
        }

        customerFromDB.setCart(gamesCart);

        return customerRepo.save(customerFromDB);
    }



    /**
     * Service method to add game to customer wishlist
     * @param id
     * @param game
     * @return
     */
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

        customerFromDB.setWishlist(gamesWishlist);

        return customerRepo.save(customerFromDB);
    }

    /**
     * Service method to remove a game from a customer wishlist
     * @param id
     * @param g
     * @return
     */
    @Transactional
    public Customer deleteGameFromCustomerWishList(int id,Game g) {
        Customer customerFromDB= customerRepo.findCustomerByRoleId(id);
        if (customerFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Customer with ID " + id + " does not exist."));
        }
        List<Game> gamesWishlist = new ArrayList<>();
        List<Game> gamesWishlistFromDb = customerFromDB.getWishlist();
        for (Game game : gamesWishlistFromDb) {
            if (game.getName() != g.getName()) {
                gamesWishlist.add(game);
            }
        }


        customerFromDB.setWishlist(gamesWishlist);

        return customerRepo.save(customerFromDB);
    }

    /**
     * This was added for integration testing purposes, to be able to add a game
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

    /**
     * Get all the payment methods of a customer
     * @param id
     * @return
     */
    @Transactional
    public List<Payment> getCustomerPaymentMethods(int id){
        Customer customer= customerRepo.findCustomerByRoleId(id);
       List <Payment> payments= (List<Payment>) paymentRepo.findAll();
        for (int i=0; i<payments.size();i++){
            if(payments.get(i).getCustomer()!=customer){
                payments.remove(payments.get(i));
            }
        }
        return payments;
    }
    /**
     * Service method to extract customerId
     * @param id
     * @return
     */
    @Transactional
    public int getCustomerCustomerId(int id) {

        List <Customer> customers= (List<Customer>) customerRepo.findAll();

        for (int i=0; i< customers.size();i++){
            if(customers.get(i).getPerson().getUserId()==id){
                return customers.get(i).getRoleId();
            }
        }
        return -1;
    }
}
