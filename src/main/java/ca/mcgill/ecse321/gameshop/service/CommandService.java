package ca.mcgill.ecse321.gameshop.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;
import jakarta.transaction.Transactional;


@Service
public class CommandService {
    // Inject CommandRepository to handle database operations
    @Autowired
    private CommandRepository commandRepo;

    // Create a new command and save it in the commandRepository
    @Transactional
    public Command createCommand(float total) {
        if (total < 0.0) {
            throw new IllegalArgumentException("Order total must be larger than 0.0");
        }

        Date today= Date.valueOf(LocalDate.now());
        Command c = new Command(today.toString(), total);

        return commandRepo.save(c);
    }

    //Retrieve all commands form the repository
    public Iterable<Command> getAllCommands(){
        return commandRepo.findAll();
    }

    //Find a command by its id
    public Command getCommandById(int id) {
        Command c = commandRepo.findCommandByCommandId(id);

        if (c == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Category with ID " + id + " does not exist."));
        }

        return c;
    }

    // Delete a command by its ID
    public void deleteCommand(int id){
        Command c = commandRepo.findCommandByCommandId(id);

        if (c == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Category with ID " + id + " does not exist."));
        }

        commandRepo.delete(c);
    }
}
