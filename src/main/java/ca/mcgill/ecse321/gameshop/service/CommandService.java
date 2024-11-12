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

    @Transactional
    public Command createCommand(Customer customer) {
        if (customer == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("Command must belong to a customer."));
        }
        float total=0;
        for(Game g : customer.getCart()){
            total+=g.getPrice()*(1-g.getPromotion());
        }
        Date today= Date.valueOf(LocalDate.now());
        Command c = new Command(today.toString(),total,customer);
        return repo.save(c);
    }

    public Command findCommandById(int cId) {
        if(cId<0){throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Command ID "+ cId+"is not valid"));}
        else if (repo.findCommandByCommandId(cId) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("There is no Command with ID " + cId + "."));
        }


        return repo.findCommandByCommandId(cId);
    }

    public void deleteCommand(int cId){
        if(cId<0){throw new GameShopException(HttpStatus.NOT_FOUND, String.format("The Command ID "+ cId+"is not valid"));}
        else if (repo.findCommandByCommandId(cId) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND,String.format("There is no Command with ID " +cId+"."));
        }
        repo.deleteById(cId);
    }
    public Iterable<Command> getAllCommands(){
       return repo.findAll();
    }

}
