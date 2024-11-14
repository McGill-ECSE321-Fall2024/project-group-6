package ca.mcgill.ecse321.gameshop.service;


/**
 * @author Joseph
 */

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Manager;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.ManagerRepository;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManagerService {


    @Autowired
    private ManagerRepository repo;
    @Autowired
    private PersonRepository personRepo;

    /**
     * Service method to create a manager
     * @param person
     * @return
     */
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

    /**
     * Service method to get a manager by id
     * @param id
     * @return
     */
    public Manager findManagerById(int id) {
        Manager manager = repo.findManagerByRoleId(id);
        if (manager == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Manager with ID " + id + " does not exist."));
        }
        return manager;
    }

    /**
     * Service method to update a manager's information
     * @param id
     * @param aUsername
     * @param aEmail
     * @param aPassword
     * @param aPhone
     * @return
     */
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

    /**
     * Service method to delete a manager
     * @param id
     */
    @Transactional
    public void deleteManager(int id) {
        Manager manager = repo.findManagerByRoleId(id);
        if (manager == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Manager with ID " + id + " does not exist."));
        }
        repo.delete(manager);
    }
}