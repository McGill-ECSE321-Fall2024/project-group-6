package ca.mcgill.ecse321.gameshop.repository;

import ca.mcgill.ecse321.gameshop.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

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
        Random rand = new Random();
        int n = rand.nextInt(1000);
        Person person = new Person("johndoe", "joseph.roustom@mail.mcgill.ca", "password123", "555-1234", n);
        person = personRepo.save(person);

        String task ="Add a game to the store";
        Employee employee = new Employee(n,person,task);
        employee = employeeRepo.save(employee);
        Employee employeeFromDB = employeeRepo.findEmployeeByRoleId(n);
        assertNotNull(employeeFromDB);
        assertEquals(employeeFromDB.getAssignedTasks(),task);
    }
}