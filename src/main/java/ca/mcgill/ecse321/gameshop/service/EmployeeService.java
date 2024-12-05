package ca.mcgill.ecse321.gameshop.service;
/**
 * @author Joseph
 */
import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.repository.EmployeeRepository;
import ca.mcgill.ecse321.gameshop.repository.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private PersonRepository personrepo;


    /**
     * Service method to get employee by id
     * @param id
     * @return
     */
    @Transactional
    public Employee getEmployeeById(int id) {

        Employee employee = repo.findEmployeeByRoleId(id);
        if (employee == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Employee with ID " + id + " does not exist."));
        }
        return employee;
    }

    /**
     * Service method to get all employees
     * @return
     */
    @Transactional
    public Iterable<Employee> getAllEmployees() {
        return  repo.findAll();
    }

    @Transactional
    public Employee addEmployee(String username, String emailAddress, String phone, String password) {

        Person person = new Person(username, emailAddress, password, phone);
        Employee employee = new Employee(person, true);
        Person personCheckExistence= personrepo.findPersonByEmail(emailAddress);

        if(personCheckExistence!=null){
            throw new GameShopException(HttpStatus.BAD_REQUEST, String.format("This employee exists already"));
        }
        if(password.length()<10){
            throw new GameShopException(HttpStatus.LENGTH_REQUIRED, String.format("Password needs to be at least 10 characters long"));
        }
        if(emailAddress==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Email can not be null"));
        }

        if(phone==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Phone number can not be null"));
        }
        if(username==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Username can not be null"));
        }
        personrepo.save(person);
        return repo.save(employee);
    }

    /**
     * method to deactivate an employee
     *
     * @param id
     * @return
     */
    @Transactional
    public Employee deactivateEmployee(int id) {

        Employee employeeFromDB = repo.findEmployeeByRoleId(id);

        if (employeeFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Employee with ID " + id + " does not exist."));
        }
        int id2=employeeFromDB.getPerson().getUserId();
        Person personFromDb= personrepo.findPersonByUserId(id2);
        if (personFromDb== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Employee with ID " + id + " does not exist."));
        }
        employeeFromDB.setActivated(false);
        personFromDb.setUsername("deactivated");
        personrepo.save(personFromDb);
        return repo.save(employeeFromDB);
    }

    /**
     * Service method to assign a task to an employee
     * @param id
     * @param task
     * @return
     */
    @Transactional
    public Employee assignTask(int id,String task) {
        Employee employeeFromDB= repo.findEmployeeByRoleId(id);
        if (employeeFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Employee with ID " + id + " does not exist."));
        }
        if(task==null){
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Task can not be null"));
        }
        List<String> assignedTasks = employeeFromDB.getAssignedTasks();
        if(employeeFromDB.getAssignedTasks()==null){
           assignedTasks=  new ArrayList<>();
        }
        assignedTasks.add(task);
        employeeFromDB.setAssignedTasks(assignedTasks);

        return repo.save(employeeFromDB);
    }

    /**
     * Service method to update an employee's information
     * @param id
     * @param aUsername
     * @param aEmail
     * @param aPassword
     * @param aPhone
     * @return
     */
    @Transactional
    public Employee updateEmployee(int id, String aUsername, String aEmail, String aPassword, String aPhone, List<String> tasks) {
        Employee employee = repo.findEmployeeByRoleId(id);

        if (employee== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Employee with ID " + id + " does not exist."));
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

        employee.getPerson().setUsername(aUsername);
        employee.getPerson().setEmail(aEmail);
        employee.getPerson().setPhone(aPhone);
        employee.getPerson().setPassword(aPassword);
        employee.setAssignedTasks(tasks);


        return repo.save(employee);
    }


    /**
     * Service method to extract employeeId
     * @param id
     * @return
     */
    @Transactional
    public int getEmployeeEmployeeId(int id) {

        List <Employee> employees= (List<Employee>) repo.findAll();

        for (int i=0; i< employees.size();i++){
            if(employees.get(i).getPerson().getUserId()==id){
                return employees.get(i).getRoleId();
            }
        }
     return -1;
    }
}
