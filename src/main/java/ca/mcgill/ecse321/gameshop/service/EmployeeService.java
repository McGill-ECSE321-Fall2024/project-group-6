package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
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


    // Find employee by ID
    @Transactional
    public Employee getEmployeeById(int id) {

        Employee employee = repo.findEmployeeByRoleId(id);
        if (employee == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Employee with ID " + id + " does not exist."));
        }
        return employee;
    }

    // Retrieve all employees from repository
    @Transactional
    public Iterable<Employee> getAllEmployees() {
        return  repo.findAll();
    }

    @Transactional
    public Employee addEmployee(String username, String emailAddress, String phone, String password) {

        Person person = new Person(username, emailAddress, password, phone);
        Employee employee = new Employee(person, true);
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
        employeeFromDB.setActivated(false);
        return repo.save(employeeFromDB);
    }
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
    @Transactional
    public Employee updateEmployee(int id, String aUsername, String aEmail, String aPassword, String aPhone) {
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


        return repo.save(employee);
    }
    @Transactional
    public List<String> getTasks(int id) {
        Employee employeeFromDB= repo.findEmployeeByRoleId(id);
        if (employeeFromDB== null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Employee with ID " + id + " does not exist."));
        }
        return employeeFromDB.getAssignedTasks();
    }
/*
    @Transactional
    public void deleteTask(int id,String task) {
        Employee employeeFromDB= repo.findEmployeeByRoleId(id);
        List<String> assignedTasks=  employeeFromDB.getAssignedTasks();
        for(int i=0;i<assignedTasks.size();i++){
            if(assignedTasks.get(i).equals(task)){
                assignedTasks.remove(i);
                break;
            }
        }
        employeeFromDB.setAssignedTasks(assignedTasks);
        repo.save(employeeFromDB);

    }

 */


}
