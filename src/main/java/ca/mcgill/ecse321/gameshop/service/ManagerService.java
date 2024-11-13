package ca.mcgill.ecse321.gameshop.service;



import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.exception.*;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import ca.mcgill.ecse321.gameshop.repository.*;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import jakarta.transaction.Transactional;

/**
 * @author joseph
 */
@Service
public class ManagerService {


    @Autowired
    private ManagerRepository repo;
    @Autowired
    private PersonRepository personRepo;

    @Transactional
    public Manager createManager(Person person) {

        Manager manager = new Manager(person);
        List<Manager> managers= (List<Manager>) repo.findAll();
        if(managers.size()==1){
            throw new GameShopException(HttpStatus.UNAUTHORIZED, String.format("Manager already exists"));
        }
        if(manager.getPerson().getPassword().length()<10){
            throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
        }
        if(manager.getPerson().getEmail()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
        }

        if(manager.getPerson().getPhone()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
        }
        if(manager.getPerson().getUsername()==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
        }
        personRepo.save(person);
        return repo.save(manager);
    }

    public Manager findManagerById(int id) {
        Manager manager = repo.findManagerByRoleId(id);
        if (manager == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Manager with ID " + id + " does not exist."));
        }
        return manager;
    }
    @Transactional
    public Manager updateManager(int id, String aUsername, String aEmail, String aPassword, String aPhone) {
       Manager manager = repo.findManagerByRoleId(id);

        if (manager== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Manager with ID " + id + " does not exist."));

        }
        if(aPassword.length()<10){
            throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
        }
        if(aEmail==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
        }

        if(aPhone==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
        }
        if(aUsername==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
        }

        manager.getPerson().setUsername(aUsername);
        manager.getPerson().setEmail(aEmail);
        manager.getPerson().setPhone(aPhone);
        manager.getPerson().setPassword(aPassword);
        //personRepo.save(manager.getPerson());

        return repo.save(manager);
    }
    @Transactional
    public void deleteManager(int id) {
        Manager manager = repo.findManagerByRoleId(id);
        if (manager == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Manager with ID " + id + " does not exist."));
        }
        repo.delete(manager);
    }
}