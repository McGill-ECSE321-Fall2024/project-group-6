package ca.mcgill.ecse321.gameshop.model;

import ca.mcgill.ecse321.gameshop.repository.EmployeeRepository;
import ca.mcgill.ecse321.gameshop.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeUnitTest {

    @Mock
    private EmployeeRepository repo;
    @InjectMocks
    private EmployeeService service;

    @Test
    public void testGetEmployeeByValidId() {
        // Arrange
        int id = 12;
        Person person = new Person("John Doe","johndoe@gmail.com", "123ABC","8195126548");
        Employee employee = new Employee(person,null);
        when(repo.findEmployeeByRoleId(id)).thenReturn(employee);
        // Act
        Employee foundEmployee = service.getEmployeeById(id);
        // Assert
        assertNotNull(foundEmployee);
        assertEquals("John Doe", foundEmployee.getPerson().getUsername());
        verify(repo,times(1)).findEmployeeByRoleId(id);

    }

    @Test
    public void testGetEmployeeByInvalidId() {
        // Arrange
        int invalidId = 15;
        when(repo.findEmployeeByRoleId(15)).thenReturn(null);
        // Act
        // Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getEmployeeById(invalidId));
        assertEquals("There is no employee with this ID:", exception.getMessage());
        verify(repo,times(1)).findEmployeeByRoleId(invalidId);
    }

    @Test
    public void testGetAllEmployees() {
        // Arrange
        Person person1 = new Person("John Doe","johndoe@gmail.com", "123ABC","8195126548");
        Person person2 = new Person("Jane Doe","janedoe@gmail.com", "ABC123","6135126545");
        Employee employee1 = new Employee(person1, null);
        Employee employee2 = new Employee(person2, null);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(repo.findAll()).thenReturn(employees);
        // Act
        Iterable<Employee> allEmployees = service.getAllEmployees();
        // Assert
        assertNotNull(allEmployees);
        List<Employee> employeeList = StreamSupport.stream(allEmployees.spliterator(), false).toList();
        assertEquals(2, employeeList.size());
        verify(repo, times(1)).findAll();

    }

}
