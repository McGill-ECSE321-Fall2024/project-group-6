package service;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.Review;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.EmployeeRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private CategoryRepository catrepo;
    @Autowired
    private GameRepository gamerepo;

    // Find employee by ID
    @Transactional
    public Employee getEmployeeById(int id) {

        Employee employee = repo.findEmployeeByRoleId(id);
        if (employee==null) {
            throw new RuntimeException("There is no employee with this ID:" + id);
        }
        return employee;
    }

    // Add Game with Manager approval
    @Transactional
    public Game addGameWithApproval(int managerId, String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL, boolean tobeAdded, Category allCategories) {

        // Manager approval check
        tobeAdded=false;
        //TODO MANAGER APPROVAL

        // Creating the game if approved
        tobeAdded=true;
        Game game = new Game(aName, aDescription, aPrice, aStockQuantity, aPhotoURL, tobeAdded, allCategories);
        return gamerepo.save(game);
    }

    // Remove Game
    @Transactional
    public void removeGame(int id) {
        gamerepo.deleteById(id);
    }

}
