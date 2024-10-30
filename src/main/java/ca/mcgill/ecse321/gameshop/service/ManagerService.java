package ca.mcgill.ecse321.gameshop.service;



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
public class ManagerService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private CategoryRepository catrepo;
    @Autowired
    private GameRepository gamerepo;

    @Autowired
    private ReviewRepository reviewrepo;

    @Transactional
    public Employee addEmployee(String username, String emailAddress,String address, String password) {

        Person person = new Person(username, emailAddress,address, password);
        Employee employee= new Employee(person,true);
        return repo.save(employee);
    }
    @Transactional
    public Employee deactivateEmployee(int id) {

      Employee employeeFromDB= repo.findEmployeeByRoleId(id);
      employeeFromDB.setActivated(false);
        return repo.save(employeeFromDB);
    }

    @Transactional
    public Category addCategory(String categoryName) {

        Category category= new Category(categoryName);
        return catrepo.save(category);
    }

    @Transactional
    public void removeCategory(int id) {
        catrepo.deleteById(id);
    }

    @Transactional
    public Game addGame(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL,boolean tobeAdded,Category... allCategories) {
        tobeAdded=true;
        Game game= new Game(aName,aDescription,aPrice,aStockQuantity,aPhotoURL,tobeAdded,allCategories);
        return gamerepo.save(game);
    }
    @Transactional
    public void removeGame(int id) {
        gamerepo.deleteById(id);
    }

    @Transactional
    public Game addPromotion(float promotion, int id) {

        Game gameFromDB= gamerepo.findGameByGameId(id);
        promotion=promotion/100;
        gameFromDB.setPromotion(promotion);
        return gamerepo.save(gameFromDB);
    }

    @Transactional
    public Game removePromotion(int id) {

        Game gameFromDB= gamerepo.findGameByGameId(id);
        //promotion=promotion/100;
        gameFromDB.setPromotion(0);
        return gamerepo.save(gameFromDB);
    }

    @Transactional
    public Review addReply(int id,String reply) {

        Review reviewFromDB= reviewrepo.findReviewByReviewId(id);
        if(reviewFromDB==null) {
            throw new RuntimeException("Review does not exist in the database");
        }else {
            reviewFromDB.setReply(reply);
        }
        //promotion=promotion/100;
        return reviewrepo.save(reviewFromDB);
    }

/*
    public Person findPersonById(int id) {
        return repo.findPersonByUserId(id);

 */
    }


