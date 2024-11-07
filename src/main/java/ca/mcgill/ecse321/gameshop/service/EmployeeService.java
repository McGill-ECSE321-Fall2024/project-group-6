package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import ca.mcgill.ecse321.gameshop.repository.EmployeeRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;
    @Autowired
    private GameRepository gameRepo;

    // Find employee by ID
    @Transactional
    public Employee getEmployeeById(int id) {

        Employee employee = repo.findEmployeeByRoleId(id);
        if (employee==null) {
            throw new RuntimeException("There is no employee with this ID:" + id);
        }
        return employee;
    }

    // Retrieve all employees from repository
    @Transactional
    public Iterable<Employee> getAllEmployees() {
        return repo.findAll();
    }

    // Add Game with Manager approval
    @Transactional
    public Game addGameWithApproval(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL) {
        boolean tobeAdded = false; // Manager approval is missing so game is added to DB but not shown to users
        Game game = new Game(aName,aDescription,aPrice,aStockQuantity,aPhotoURL);
        return gameRepo.save(game);
    }

    // Remove Game
    @Transactional
    public void removeGame(int id) {
        gameRepo.deleteById(id);
    }

}
