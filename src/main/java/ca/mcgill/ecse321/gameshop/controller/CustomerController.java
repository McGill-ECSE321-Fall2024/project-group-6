package ca.mcgill.ecse321.gameshop.controller;
/**
 * @author Joseph and Marine
 */

import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.service.CustomerService;
import ca.mcgill.ecse321.gameshop.service.GameService;
import ca.mcgill.ecse321.gameshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {


    @Autowired
    private CustomerService customerService;
    @Autowired
    private GameService gameService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private GameRepository gameRepo;
    /**
     * Create a customer
     * @param customerToCreate
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PostMapping("/customers")
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customerToCreate) {
    Person person= new Person(customerToCreate.getUsername(),customerToCreate.getEmail(),customerToCreate.getPassword(),customerToCreate.getPhone());
        Customer customer = customerService.createCustomer(person,customerToCreate.getShippingAddress());

        return new CustomerResponseDto(customer);
    }

    /**
     * Only for testing purposes, to add a game to a customers cart
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PostMapping("/customers/test")
    public GameResponseDto createGameForCustomerTesting(@RequestBody GameRequestDto g){
        Game game = customerService.addGame(g.getName(), g.getDescription(), g.getPrice(), g.getStockQuantity(), g.getPhotoURL());
        return new GameResponseDto(game);
    }

    /**
     * get Customer
     * @param cid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/customers/{cid}")
    public CustomerResponseDto getCustomerById(@PathVariable int cid) {
        Customer customer=customerService.getCustomerByID(cid);

        return new CustomerResponseDto(customer);
    }

    /**
     * Get all customers
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/customers")
    public CustomerListDto getAllCustomers() {
        List<CustomerResponseDto> customers = new ArrayList<>();
        for (Customer c: customerService.getAllCustomers()) {
            customers.add(new CustomerResponseDto(c));
        }
        return new CustomerListDto(customers);
    }

    /**
     * Delete a customer
     * @param cid
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @DeleteMapping("/customers/{cid}")
    public void deleteCustomer(@PathVariable int cid) {
        List <Payment> paymentsToDelete= customerService.getCustomerPaymentMethods(cid);
        for (Payment payment : paymentsToDelete) {
            paymentService.deletePayment(payment.getPaymentId());
        }
        customerService.deleteCustomer(cid);

    }

    /**
     * update a customer's information
     * @param cid
     * @param customer
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/customers/{cid}")
    public CustomerResponseDto updateCustomer(@PathVariable int cid, @RequestBody CustomerRequestDto customer) {
       Customer c = customerService.updateCustomer(cid, customer.getUsername(),customer.getEmail(),customer.getPassword(),customer.getPhone(),customer.getShippingAddress());

        return new CustomerResponseDto(c);
    }

    /**
     * Add a game to a customer's cart
     * @param cid
     * @param gid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/customers/{cid}/cart/add/{gid}")
    public CustomerResponseDto addGameToCart(@PathVariable int cid, @PathVariable int gid) {
            Game game= gameRepo.findGameByGameId(gid);
            Customer c = customerService.addGameToCustomerCart(cid, game);
            return new CustomerResponseDto(c);
        }


    /**
     * remove a game from customer's cart
     * @param cid
     * @param gid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/customers/{cid}/cart/{gid}")
    public CustomerResponseDto removeGameFromCart(@PathVariable int cid, @PathVariable int gid) {
        Game g= gameRepo.findGameByGameId(gid);
        Customer c = customerService.deleteGameFromCustomerCart(cid, g);
        return new CustomerResponseDto(c);
    }

    /**
     * Add a game to a customer's wishlist
     * @param cid
     * @param gid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/customers/{cid}/wishlist/add/{gid}")
    public CustomerResponseDto addGameToWishlist(@PathVariable int cid, @PathVariable int gid) {
        Game game= gameRepo.findGameByGameId(gid);
        Customer c = customerService.addGameToCustomerWishList(cid, game);
        return new CustomerResponseDto(c);
    }

    /**
     * Remove a game from a customer wishlist
     * @param cid
     * @param gid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @PutMapping("/customers/{cid}/wishlist/{gid}")
    public CustomerResponseDto deleteGameFromWishlist(@PathVariable int cid, @PathVariable int gid) {

        Game g= gameRepo.findGameByGameId(gid);
        Customer c = customerService.deleteGameFromCustomerWishList(cid, g);
        return new CustomerResponseDto(c);
    }

    /**
     * Controller method to get all the payment methods of a Customer
     * @param cid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/customers/{cid}/payments")
    public PaymentListDto getAllCustomerPaymentMethods(@PathVariable int cid) {
        List<PaymentResponseDto> payments = new ArrayList<>();
        for (Payment p: customerService.getCustomerPaymentMethods(cid)) {
            payments.add(new PaymentResponseDto(p));
        }
        return new PaymentListDto(payments);

    }
    /**
     * Method to extract customerId using userId, no need to integrate or unit test
     * @param cid
     * @return
     */
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/customers/id/{cid}")
    public int getCustomerCustomerId(@PathVariable int cid) {
        return customerService.getCustomerCustomerId(cid);
    }
}
