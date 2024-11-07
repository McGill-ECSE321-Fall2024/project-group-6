package ca.mcgill.ecse321.gameshop.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.model.Manager;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.ManagerRepository;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
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
        return repo.save(manager);
    }

    public Manager findManagerById(int id) {
        Manager manager = repo.findManagerByRoleId(id);
        if (manager == null) {
            throw new RuntimeException("This manager does not exist in the database");
        }
        return manager;
    }
    @Transactional
    public Manager updateManager(int id, String aUsername, String aEmail, String aPassword, String aPhone) {
        Manager manager = repo.findManagerByRoleId(id);

        if (manager== null) {
            throw new IllegalArgumentException("Employee with ID " + id + " does not exist.");
        }

        manager.getPerson().setUsername(aUsername);
        manager.getPerson().setEmail(aEmail);
        manager.getPerson().setPhone(aPhone);
        manager.getPerson().setPassword(aPassword);
        personRepo.save(manager.getPerson());

        return repo.save(manager);
    }
    @Transactional
    public void deleteManager(int id) {
        Manager manager = repo.findManagerByRoleId(id);
        if (manager == null) {
            throw new RuntimeException("This manager does not exist in the database");
        }
        repo.delete(manager);
    }
}