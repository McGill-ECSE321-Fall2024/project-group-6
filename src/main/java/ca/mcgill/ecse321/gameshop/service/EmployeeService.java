package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.repository.EmployeeRepository;
import ca.mcgill.ecse321.gameshop.repository.*;
import jakarta.transaction.Transactional;

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
            throw new RuntimeException("There is no employee with this ID:" + id);
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

        Person person = new Person(username, emailAddress, phone, password);
        Employee employee = new Employee(person, true);
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
        employeeFromDB.setActivated(false);
        return repo.save(employeeFromDB);
    }
    @Transactional
    public Employee assignTask(int id,String task) {
        Employee employeeFromDB= repo.findEmployeeByRoleId(id);
        List<String> assignedTasks=  employeeFromDB.getAssignedTasks();
        assignedTasks.add(task);
        employeeFromDB.setAssignedTasks(assignedTasks);

        return repo.save(employeeFromDB);
    }
    @Transactional
    public Employee updateEmployee(int id, String aUsername, String aEmail, String aPassword, String aPhone) {
        Employee employee = repo.findEmployeeByRoleId(id);

        if (employee== null) {
            throw new IllegalArgumentException("Employee with ID " + id + " does not exist.");
        }

        employee.getPerson().setUsername(aUsername);
        employee.getPerson().setEmail(aEmail);
        employee.getPerson().setPhone(aPhone);
        employee.getPerson().setPassword(aPassword);
        personrepo.save(employee.getPerson());

        return repo.save(employee);
    }
    @Transactional
    public List<String> getTasks(int id) {
        Employee employeeFromDB= repo.findEmployeeByRoleId(id);
        List<String> assignedTasks=  employeeFromDB.getAssignedTasks();

        return assignedTasks;
    }
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


}
