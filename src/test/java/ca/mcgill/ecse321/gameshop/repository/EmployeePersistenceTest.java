package ca.mcgill.ecse321.gameshop.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.Person;

@SpringBootTest
public class EmployeePersistenceTest {


    // private WishlistRepository wishRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private PersonRepository personRepo;

    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        employeeRepo.deleteAll();
        personRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadEmployee() {

        Person person = new Person("johndoe", "joseph.roustom@mail.mcgill.ca", "password123", "555-1234");
        List <String> tasks= new ArrayList<>();
        tasks.add("hey");
        tasks.add("bye");
        //String task ="Add a game to the store";
        Employee employee = new Employee(person,tasks);
        employee = employeeRepo.save(employee);
        Employee employeeFromDB = employeeRepo.findEmployeeByRoleId(employee.getRoleId());
        assertNotNull(employeeFromDB);
        assertEquals(employeeFromDB.getActivated(),false);
        assertEquals(employeeFromDB.getAssignedTasks(),tasks);
    }
}