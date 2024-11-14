package ca.mcgill.ecse321.gameshop.repository;


import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.model.Command;

@SpringBootTest
public class CommandPersistenceTest {


    // private WishlistRepository wishRepo;

    @Autowired
    private CommandRepository commandRepo;

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

        paymentRepo.deleteAll();
        customerRepo.deleteAll();
        personRepo.deleteAll();
        paymentRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadCommand() {

        Command command=new Command( "13-10-2024", 75.6F);
        command=commandRepo.save(command);


        Command commandFromDb= commandRepo.findCommandByCommandId(command.getCommandId());
        /**
         * Object test
         */
        assertNotNull(commandFromDb);
        /**
         * Relationship test
         */
       // assertEquals(commandFromDb.getCart().getCartId(),cart.getCartId());
        /**
         * variable test
         */
        assertEquals(commandFromDb.getTotalPrice(),75.6F);

    }
}
