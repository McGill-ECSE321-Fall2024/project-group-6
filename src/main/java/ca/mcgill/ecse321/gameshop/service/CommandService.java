package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;


@Service
public class CommandService {
    @Autowired
    private CommandRepository repo;

    @Transactional
    public Command createCommand(float total) {
        if (total < 0) {
            throw new IllegalArgumentException("Order total must be large than 0.0");
        }
        Date today= Date.valueOf(LocalDate.now());
        Command c = new Command(today.toString(),total);
        return repo.save(c);
    }

    public Command findCommandById(int cId){
        return repo.findCommandByCommandId(cId);
    }

}
