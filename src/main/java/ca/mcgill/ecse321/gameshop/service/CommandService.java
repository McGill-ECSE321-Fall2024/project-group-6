package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;


@Service
public class CommandService {

    @Autowired
    private CommandRepository repo;

    /**
     * @author Maissa
     * Creates a new command for a given customer, calculates the total price based on
     * the prices and promotions of games in the customer's cart.
     *
     * @param customer
     * @return Created command.
     * @throws GameShopException
     */
    @Transactional
    public Command createCommand(Customer customer) {
        if (customer == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "Command must belong to a customer.");
        }
        //Calculates total price of command
        float total = 0;
        for (Game g : customer.getCart()) {
            total += g.getPrice() * (1 - g.getPromotion());
        }

        Date today = Date.valueOf(LocalDate.now());
        Command c = new Command(today.toString(), total, customer);
        return repo.save(c);
    }

    /**
     * @author Maissa
     * Finds a command by its ID.
     *
     * @param cId
     * @return Command with cId.
     * @throws GameShopException
     */
    public Command findCommandById(int cId) {
        if (cId < 0) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "The Command ID " + cId + " is not valid.");
        } else if (repo.findCommandByCommandId(cId) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "There is no Command with ID " + cId + ".");
        }

        return repo.findCommandByCommandId(cId);
    }

    /**
     * @author Maissa
     * Deletes a command by its ID.
     *
     * @param cId
     * @throws GameShopException
     */
    public void deleteCommand(int cId) {
        if (cId < 0) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "The Command ID " + cId + " is not valid.");
        } else if (repo.findCommandByCommandId(cId) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "There is no Command with ID " + cId + ".");
        }

        repo.deleteById(cId);
    }

    /**
     * @author Maissa
     * Retrieves all commands stored in the repository.
     *
     * @return Commands.
     */
    public Iterable<Command> getAllCommands() {
        return repo.findAll();
    }
}
