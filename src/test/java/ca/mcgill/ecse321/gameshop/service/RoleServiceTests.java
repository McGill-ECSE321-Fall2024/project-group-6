package ca.mcgill.ecse321.gameshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.*;
import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gameshop.exception.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.*;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class RoleServiceTests {
    @Mock
    private RoleRepository mockRepo;
    @InjectMocks
    private RoleService service;

    private static final String VALID_NAME = "Will";
    private static final String VALID_EMAIL = "abcdefghijklmnop@gmail.com";
    private static final String VALID_PASSWORD = "NotTimHeres";
    private static final String VALID_PHONE ="123456";
    private static final int ID= 12;

    //Person person= new Person(VALID_NAME, VALID_EMAIL,VALID_PASSWORD, VALID_PHONE);
    @Test
    public void testCreateValidRoleCustomer() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        when(mockRepo.save(any(Role.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act

        Role createdRole = service.createRoleCustomer(VALID_NAME, VALID_EMAIL,VALID_PHONE,VALID_PASSWORD, "123 Sherbrooke West");
      //  String username, String emailAddress, String phone, String password,String address
        // Assert
        assertNotNull(createdRole);
        assertEquals(VALID_NAME, createdRole.getPerson().getUsername());
        assertEquals(VALID_EMAIL, createdRole.getPerson().getEmail());
        assertEquals(VALID_PASSWORD, createdRole.getPerson().getPassword());
        assertEquals(VALID_PHONE, createdRole.getPerson().getPhone());

        verify(mockRepo, times(1)).save(createdRole);
    }
    @Test
    public void testCreateValidRoleEmployee() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        when(mockRepo.save(any(Role.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
String email="abcdefghijklmno@gmail.com";
        Role createdRole = service.createRoleEmployee(VALID_NAME, email, VALID_PHONE,VALID_PASSWORD);

        // Assert
        assertNotNull(createdRole);
        assertEquals(VALID_NAME, createdRole.getPerson().getUsername());
        assertEquals(email, createdRole.getPerson().getEmail());
        assertEquals(VALID_PASSWORD, createdRole.getPerson().getPassword());
        assertEquals(VALID_PHONE, createdRole.getPerson().getPhone());

        verify(mockRepo, times(1)).save(createdRole);
    }
    @Test
    public void testCreateRoleWithInvalidPhone() {

        String email="abcdefghijklmn@gmail.com";


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createRoleEmployee(VALID_NAME, email,null,VALID_PASSWORD));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Phone number can not be null", ex.getMessage());
    }
    @Test
    public void testCreateRoleWithInvalidUsername() {

        String email="abcdefghijklm@gmail.com";


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createRoleEmployee(null, email, VALID_PHONE,VALID_PASSWORD));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Username can not be null", ex.getMessage());
    }

    @Test
    public void testCreateRoleWithInvalidEmail() {

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createRoleEmployee(VALID_NAME, null,VALID_PHONE, VALID_PASSWORD));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Email can not be null", ex.getMessage());
    }
    @Test
    public void testCreateRoleWithInvalidPassword() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p

        String email="abcdefghijkl@gmail.com";



        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createRoleEmployee(VALID_NAME, email, VALID_PHONE,"123"));
        assertEquals(HttpStatus.LENGTH_REQUIRED, ex.getStatus());
        assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
    }
    @Test
    public void testCreateRoleWithInvalidShippingAddress() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p

        String email="abcdefghijk@gmail.com";

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createRoleCustomer(VALID_NAME, email, VALID_PHONE,VALID_PASSWORD,null));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Shipping Address can not be null", ex.getMessage());
    }



    @Test
    public void testGetRoleByValidId() {
        // Arrange
        String email="abcdefghij@gmail.com";
        Person person= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);
        when(mockRepo.findRoleByRoleId(ID)).thenReturn(new Manager(person));

        // Act
        Role role= mockRepo.findRoleByRoleId(ID);

        // Assert
        assertNotNull(role);
        assertEquals(VALID_NAME, role.getPerson().getUsername());
        assertEquals(email, role.getPerson().getEmail());
        assertEquals(VALID_PASSWORD,role.getPerson().getPassword());
        assertEquals(VALID_PHONE, role.getPerson().getPhone());
    }

    @Test
    public void testGetRoleByInvalidId() {
        // Arrange
        // Act
        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.getRoleById(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Role with ID " + ID + " does not exist.", ex.getMessage());
    }
    @Test
    public void testGetAllRoles() {

        // Arrange
        Person p1 = new Person(VALID_NAME, "abcd@gmail.com", VALID_PASSWORD, VALID_PHONE);
        Manager m1= new Manager(p1);
        Person p2 = new Person("Alice", "abc@gmail.com", "password123456", "+1(514)9876543");
        Manager m2= new Manager(p2);
        List<Role> roles = Arrays.asList(m1, m2);
        when(mockRepo.findAll()).thenReturn(roles);

        // Act
        Iterable<Role> rolesList = service.getAllRoles();
        List<Role> listOfRoles = new ArrayList<>();
        rolesList.forEach(listOfRoles::add);

        // Assert
        assertNotNull(listOfRoles);
        assertEquals(2, listOfRoles.size());
        assertEquals(VALID_NAME, listOfRoles.get(0).getPerson().getUsername());
        assertEquals("Alice", listOfRoles.get(1).getPerson().getUsername());
    }


    @Test
    public void testDeleteRoleByValidId() {
        // Arrange
        String email="a@gmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Role existingManagerRole= new Manager(aPerson);

        when(mockRepo.findRoleByRoleId(ID)).thenReturn(existingManagerRole);

        // Act
        service.deleteRole(ID);

        // Assert
       // assertNull(mockRepo.findRoleByRoleId(ID));
        verify(mockRepo, times(1)).delete(existingManagerRole);
    }

    @Test
    public void testDeleteRoleByInvalidId() {

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.deleteRole(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Role with ID " + ID + " does not exist.", ex.getMessage());
    }
}