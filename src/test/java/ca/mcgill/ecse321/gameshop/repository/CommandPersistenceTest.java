package ca.mcgill.ecse321.gameshop.repository;

import ca.mcgill.ecse321.gameshop.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Random;

@SpringBootTest
public class CommandPersistenceTest {


    // private WishlistRepository wishRepo;

    @Autowired
    private CommandRepository commandRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private PaymentRepository paymentRepo;

    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        commandRepo.deleteAll();
        cartRepo.deleteAll();
        paymentRepo.deleteAll();
        customerRepo.deleteAll();
        personRepo.deleteAll();
        paymentRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadCommand() {
        Random rand = new Random();
        int n = rand.nextInt(1000000000);
        int indexCart= n+10;
        int indexPay=n+20;
        int indexCommand=n+30;
        Person person = new Person("johndoe", "r@email.com", "password123", "555-1234", n);
        person = personRepo.save(person);


        Customer customer = new Customer(n, person, "1234 Montreal");
        customer = customerRepo.save(customer);
        Cart cart = new Cart(indexCart, customer);
        cart = cartRepo.save(cart);

        Payment payment = new Payment("1234 Toronto",123456789,"05/27",444,50,indexPay);
        payment = paymentRepo.save(payment);

        Command command=new Command(indexCommand, "13-10-2024", 75.6F, payment, cart);
        command=commandRepo.save(command);


        Command commandFromDb= commandRepo.findCommandByCommandId(indexCommand);
        /**
         * Object test
         */
        assertNotNull(commandFromDb);
        /**
         * Relationship test
         */
        assertEquals(commandFromDb.getCart().getCartId(),indexCart);
        /**
         * variable test
         */
        assertEquals(commandFromDb.getTotalPrice(),75.6F);

    }
}
