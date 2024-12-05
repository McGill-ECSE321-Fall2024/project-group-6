package ca.mcgill.ecse321.gameshop.service;
/**
 * @author Joseph
 */

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Employee;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.EmployeeRepository;
import ca.mcgill.ecse321.gameshop.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository mockRepo;
    @Mock
    private PersonRepository repo;
    @InjectMocks
    private EmployeeService service;

    private static final String VALID_NAME = "Will";
    private static final String VALID_EMAIL = "will@gmail.com";
    private static final String VALID_PASSWORD = "NotTimHeres";
    private static final String VALID_PHONE ="123456";
    private static final int ID= 10;

    //Person person= new Person(VALID_NAME, VALID_EMAIL,VALID_PASSWORD, VALID_PHONE);

    /**
     * Test valid creation of an employee
     */
    @Test
    public void testCreateValidEmployee() {
        // Arrange
        when(mockRepo.save(any(Employee.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(repo.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        Employee createdEmployee = service.addEmployee(VALID_NAME, VALID_EMAIL,VALID_PHONE, VALID_PASSWORD);

        // Assert
        assertNotNull(createdEmployee);
        assertEquals(VALID_NAME, createdEmployee.getPerson().getUsername());
        assertEquals(VALID_EMAIL, createdEmployee.getPerson().getEmail());
        assertEquals(VALID_PASSWORD, createdEmployee.getPerson().getPassword());
        assertEquals(VALID_PHONE, createdEmployee.getPerson().getPhone());
        verify(mockRepo, times(1)).save(createdEmployee);
    }
    /**
     * Test creation of an employee with invalid phone
     */
    @Test
    public void testCreateEmployeeWithInvalidPhone() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        // when(mockRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        String email="qrstuvwxyz@gmail.com";


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.addEmployee(VALID_NAME, email,null, VALID_PASSWORD));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Phone number can not be null", ex.getMessage());
    }
    /**
     * Test creation of an employee with invalid username
     */
    @Test
    public void testCreateEmployeeWithInvalidUsername() {

        String email="qrstuvwxy@gmail.com";


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.addEmployee(null, email,VALID_PHONE, VALID_PASSWORD));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Username can not be null", ex.getMessage());
    }
    /**
     * Test creation of an employee with invalid email
     */
    @Test
    public void testCreateEmployeeWithInvalidEmail() {

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.addEmployee(VALID_NAME, null,VALID_PHONE, VALID_PASSWORD));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Email can not be null", ex.getMessage());
    }
    /**
     * Test creation of an employee with invalid password length
     */
    @Test
    public void testCreateEmployeeWithInvalidPassword() {

        String email="qrstuvwx@gmail.com";


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.addEmployee(VALID_NAME, email,VALID_PHONE, "123"));
        assertEquals(HttpStatus.LENGTH_REQUIRED, ex.getStatus());
        assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
    }

    /**
     * Test get employee with valid id
     */
    @Test
    public void testGetEmployeeByValidId() {
        // Arrange
        String email="qrstuvw@gmail.com";

        Person person= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);
        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(new Employee(person,true));

        // Act
        Employee employee= mockRepo.findEmployeeByRoleId(ID);

        // Assert
        assertNotNull(employee);
        assertEquals(VALID_NAME, employee.getPerson().getUsername());
        assertEquals(email, employee.getPerson().getEmail());
        assertEquals(VALID_PASSWORD,employee.getPerson().getPassword());
        assertEquals(VALID_PHONE, employee.getPerson().getPhone());
        assertEquals(true, employee.getActivated());
    }
    /**
     * Test get employee with invalid id
     */
    @Test
    public void testReadEmployeeByInvalidId() {

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.getEmployeeById(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Employee with ID " + ID + " does not exist.", ex.getMessage());
    }
    /**
     * Update employee with valid info and id
     */
    @Test
    public void testUpdateEmployeeByValidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "onetwothreefourfivegmail.com";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654321";

        String email="qrstuvw@gmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Employee exstingEmployee=new Employee(aPerson,true);

        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(exstingEmployee);
        // Mock the save method to return the updated person when save() is called
        when(mockRepo.save(any(Employee.class))).thenAnswer((InvocationOnMock iom) -> {
            Employee updatedEmployee = iom.getArgument(0);
            updatedEmployee.getPerson().setUsername(updatedName);
            updatedEmployee.getPerson().setEmail(updatedEmail);
            updatedEmployee.getPerson().setPassword(updatedPassword);
            updatedEmployee.getPerson().setPhone(updatedPhone);
            return updatedEmployee;
        });

        // Act
        Employee updatedEmployee = service.updateEmployee(ID, updatedName, updatedEmail, updatedPassword, updatedPhone,null);

        // Assert
        assertNotNull(updatedEmployee);
        assertEquals(updatedName, updatedEmployee.getPerson().getUsername());
        assertEquals(updatedEmail, updatedEmployee.getPerson().getEmail());
        assertEquals(updatedPassword, updatedEmployee.getPerson().getPassword());
        assertEquals(updatedPhone, updatedEmployee.getPerson().getPhone());

        verify(mockRepo, times(1)).save(updatedEmployee);
    }

    /**
     * Update employee with invalid password length
     */
    @Test
    public void testUpdateEmployeeByInvalidPasswordLength() {

        String updatedName = "UpdatedBob";
        String updatedEmail = "onetwothreefourfivgmail.com";
        String updatedPassword = "new";
        String updatedPhone = "+1(514)7654321";

        String email="qrstuv@gmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Employee exstingEmployee=new Employee(aPerson,true);

        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(exstingEmployee);



        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateEmployee(ID, updatedName, updatedEmail, updatedPassword, updatedPhone,null));
        assertEquals(HttpStatus.LENGTH_REQUIRED, ex.getStatus());
        assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
    }
    /**
     * Update employee with invalid username
     */
    @Test
    public void testUpdateEmployeeByNullUsername() {
        String updatedName = null;
        String updatedEmail = "onetwothreefourfigmail.com";
        String updatedPassword = "12345678900000";
        String updatedPhone = "+1(514)7654321";

        String email="qrstu@gmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Employee exstingEmployee=new Employee(aPerson,true);

        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(exstingEmployee);
        // Mock the save method to return the updated person when save() is called


        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateEmployee(ID, updatedName, updatedEmail, updatedPassword, updatedPhone,null));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Username can not be null", ex.getMessage());
    }
    /**
     * Update employee with invalid phone number
     */
    @Test
    public void testUpdateEmployeeByNullPhone() {
        // Arrange
        String updatedName = "Joseph";
        String updatedEmail = "onetwothreefourfgmail.com";
        String updatedPassword = "12345678900000";
        String updatedPhone = null;

        String email="qrst@gmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Employee exstingEmployee=new Employee(aPerson,true);

        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(exstingEmployee);



        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateEmployee(ID, updatedName, updatedEmail, updatedPassword, updatedPhone,null));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Phone number can not be null", ex.getMessage());
    }
    /**
     * Update employee with invalid email
     */
    @Test
    public void testUpdateEmployeeByNullEmail() {
        // Arrange
        String updatedName = "Joseph";
        String updatedEmail = null;
        String updatedPassword = "12345678900000";
        String updatedPhone = "+1(514)7654321";

        String email="qrs@gmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Employee exstingEmployee=new Employee(aPerson,true);

        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(exstingEmployee);
        // Mock the save method to return the updated person when save() is called


        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateEmployee(ID, updatedName, updatedEmail, updatedPassword, updatedPhone,null));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Email can not be null", ex.getMessage());
    }
    /**
     * Update employee with invalid id
     */
    @Test
    public void testUpdateEmployeeByInvalidId() {

        String email="qr@gmail.com";
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateEmployee(ID, VALID_NAME, email, VALID_PASSWORD, VALID_PHONE,null));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Employee with ID " + ID + " does not exist.", ex.getMessage());
    }
    /**
     * test successfully get all employees
     */

    @Test
    public void testGetAllEmployees() {

        // Arrange
        Person p1 = new Person(VALID_NAME, "lmnop@gmail.com", VALID_PASSWORD, VALID_PHONE);
        Employee e1= new Employee(p1,true);
        Person p2 = new Person("Rodney", "lmno@gmail.com", "password123456", "+1(514)9876543");
        Employee e2= new Employee(p2,true);
        List<Employee> employees = Arrays.asList(e1, e2);
        when(mockRepo.findAll()).thenReturn(employees);

        // Act
        Iterable<Employee> employeesList = service.getAllEmployees();
        List<Employee> listOfEmployees = new ArrayList<>();
        employeesList.forEach(listOfEmployees::add);

        // Assert
        assertNotNull(listOfEmployees);
        assertEquals(2, listOfEmployees.size());
        assertEquals(VALID_NAME, listOfEmployees.get(0).getPerson().getUsername());
        assertEquals("Rodney", listOfEmployees.get(1).getPerson().getUsername());
    }
    /**
     * test successfully delete employee with valid id
     */

    @Test
    public void testDeleteEmployeeByValidId() {
        // Arrange
        String email="q@gmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Employee existingEmployee= new Employee(aPerson,true);

        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(existingEmployee);
        when(repo.findPersonByUserId(0)).thenReturn(existingEmployee.getPerson());


        service.deactivateEmployee(ID);

        assertEquals(false, existingEmployee.getActivated());


    }
    /**
     * test unsuccessfully delete employee with invalid id
     */
    @Test
    public void testDeleteEmployeeByInvalidId() {

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.deactivateEmployee(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Employee with ID " + ID + " does not exist.", ex.getMessage());
    }
    /**
     * test successfully assign a task to an employee
     */
    @Test
    public void testAssignEmployeeWithValidIdAValidTask() {
        String updatedName = "UpdatedBob";
        String updatedEmail = "onetwofive@gmail.com";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654321";

        String email="qzyx@gmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Employee exstingEmployee=new Employee(aPerson,true);

        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(exstingEmployee);
        // Mock the save method to return the updated person when save() is called
        when(mockRepo.save(any(Employee.class))).thenAnswer((InvocationOnMock iom) -> {
            Employee updatedEmployee = iom.getArgument(0);
            updatedEmployee.getPerson().setUsername(updatedName);
            updatedEmployee.getPerson().setEmail(updatedEmail);
            updatedEmployee.getPerson().setPassword(updatedPassword);
            updatedEmployee.getPerson().setPhone(updatedPhone);
            return updatedEmployee;
        });

        // Act
        Employee updatedEmployeeList = service.assignTask(ID, "Do XYZ");

        // Assert
        assertNotNull(updatedEmployeeList);
        assertEquals(true, updatedEmployeeList.getAssignedTasks().contains("Do XYZ"));

        verify(mockRepo, times(1)).save(updatedEmployeeList);
    }
    /**
     * test  assign a task to a non-existent employee (invalid id)
     */
    @Test
    public void testAssignEmployeeWithInvalidIdATask() {
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.assignTask(ID,"Do XYZ"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Employee with ID " + ID + " does not exist.", ex.getMessage());
    }
    /**
     * test  assign invalid (null) task to employee
     */
    @Test
    public void testAssignEmployeeWithInvalidTask() {


        String email="wxyz@gmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Employee exstingEmployee=new Employee(aPerson,true);

        when(mockRepo.findEmployeeByRoleId(ID)).thenReturn(exstingEmployee);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.assignTask(ID, null));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Task can not be null", ex.getMessage());
    }


}