package ca.mcgill.ecse321.gameshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class CustomerServiceTests {
    @Mock
    private CustomerRepository mockRepo;
    @InjectMocks
    private CustomerService service;

    private static final String VALID_NAME = "Will";
    private static final String VALID_EMAIL = "william@hotmail.com";
    private static final String VALID_PASSWORD = "NotTimHeres";
    private static final String VALID_PHONE ="123456";
    private static final String VALID_ADDRESS ="123 Sherbrooke West";
    private static final int ID= 10;

    //Person person= new Person(VALID_NAME, VALID_EMAIL,VALID_PASSWORD, VALID_PHONE);
    @Test
    public void testCreateValidCustomer() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        when(mockRepo.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Person person= new Person(VALID_NAME, VALID_EMAIL,VALID_PASSWORD, VALID_PHONE);
        Customer createdCustomer = service.createCustomer(person,VALID_ADDRESS);

        // Assert
        assertNotNull(createdCustomer);
        assertEquals(VALID_NAME, createdCustomer.getPerson().getUsername());
        assertEquals(VALID_EMAIL, createdCustomer.getPerson().getEmail());
        assertEquals(VALID_PASSWORD, createdCustomer.getPerson().getPassword());
        assertEquals(VALID_PHONE, createdCustomer.getPerson().getPhone());
        verify(mockRepo, times(1)).save(createdCustomer);
    }
    @Test
    public void testCreateCustomerWithInvalidPhone() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        // when(mockRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        String email="abcdefghijklmnop@hotmail.com";
        Person p= new Person(VALID_NAME, email,VALID_PASSWORD, null);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createCustomer(p, VALID_ADDRESS));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Phone number can not be null", ex.getMessage());
    }
    @Test
    public void testCreateCustomerWithInvalidUsername() {

        String email="abcdefghijklmn@hotmail.com";
        Person p= new Person(null, email,VALID_PASSWORD, VALID_PHONE);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createCustomer(p,VALID_ADDRESS));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Username can not be null", ex.getMessage());
    }

    @Test
    public void testCreateCustomerWithInvalidEmail() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p
        Person p= new Person(VALID_NAME, null,VALID_PASSWORD, VALID_PHONE);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createCustomer(p,VALID_ADDRESS));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Email can not be null", ex.getMessage());
    }
    @Test
    public void testCreateCustomerWithInvalidPassword() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p

        String email="abcdefghijklm@hotmail.com";
        Person p= new Person(VALID_NAME, email,"123", VALID_PHONE);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createCustomer(p,VALID_ADDRESS));
        assertEquals(HttpStatus.LENGTH_REQUIRED, ex.getStatus());
        assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
    }
    @Test
    public void testCreateCustomerWithInvalidAddress() {
        // Arrange
        // Whenever mockRepo.save(p) is called, return p

        String email="abcdefghijkl@hotmail.com";
        Person p= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.createCustomer(p,null));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Shipping address can not be null", ex.getMessage());
    }



    @Test
    public void testGetCustomerByValidId() {
        // Arrange
        String email="abcdefghijk@hotmail.com";
        Person person= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);
        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(new Customer(person,VALID_ADDRESS));

        // Act
        Customer customer= mockRepo.findCustomerByRoleId(ID);

        // Assert
        assertNotNull(customer);
        assertEquals(VALID_NAME, customer.getPerson().getUsername());
        assertEquals(email, customer.getPerson().getEmail());
        assertEquals(VALID_PASSWORD,customer.getPerson().getPassword());
        assertEquals(VALID_PHONE, customer.getPerson().getPhone());
        assertEquals(VALID_ADDRESS, customer.getShippingAddress());
    }

    @Test
    public void testReadCustomerByInvalidId() {
        // Arrange
        // Act
        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.getCustomerByID(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Customer with ID " + ID + " does not exist.", ex.getMessage());
    }
    @Test
    public void testGetAllCustomers() {
        Person p1 = new Person(VALID_NAME, "lmnop@hotmail.com", VALID_PASSWORD, VALID_PHONE);
        Customer c1 = new Customer(p1, VALID_ADDRESS);
        Person p2 = new Person("Rodney", "lmno@hotmail.com", "password123456", "+1(514)9876543");
        Customer c2 = new Customer(p2, "123 Sherbrooke East");
        List<Customer> customers = Arrays.asList(c1, c2);
        when(mockRepo.findAll()).thenReturn(customers);

        // Act
        Iterable<Customer> customersList = service.getAllCustomers();
        List<Customer> listOfCustomers = new ArrayList<>();
        customersList.forEach(listOfCustomers::add);

        // Assert
        assertNotNull(listOfCustomers);
        assertEquals(2, listOfCustomers.size());
        assertEquals(VALID_NAME, listOfCustomers.get(0).getPerson().getUsername());
        assertEquals("Rodney", listOfCustomers.get(1).getPerson().getUsername());
        assertEquals(VALID_ADDRESS,c1.getShippingAddress());
        assertEquals("123 Sherbrooke East",c2.getShippingAddress());
    }


    @Test
    public void testUpdateCustomerByValidId() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedbob@hotmail.com";
        String updatedPassword = "newpassword123";
        String updatedPhone = "+1(514)7654321";

        String email="abcdefghij@hotmail.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Customer existingCustomer=new Customer(aPerson,VALID_ADDRESS);

        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(existingCustomer);
        // Mock the save method to return the updated person when save() is called
        when(mockRepo.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> {
            Customer updatedCustomer = iom.getArgument(0);
            updatedCustomer.getPerson().setUsername(updatedName);
            updatedCustomer.getPerson().setEmail(updatedEmail);
            updatedCustomer.getPerson().setPassword(updatedPassword);
            updatedCustomer.getPerson().setPhone(updatedPhone);
            updatedCustomer.setShippingAddress("123 Sherbrooke East");

            return updatedCustomer;
        });

        // Act
        Customer updatedCustomer = service.updateCustomer(ID, updatedName,updatedEmail,updatedPassword,updatedPhone,"123 Sherbrooke East");

        // Assert
        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getPerson().getUsername());
        assertEquals(updatedEmail, updatedCustomer.getPerson().getEmail());
        assertEquals(updatedPassword, updatedCustomer.getPerson().getPassword());
        assertEquals(updatedPhone, updatedCustomer.getPerson().getPhone());
        assertEquals("123 Sherbrooke East", updatedCustomer.getShippingAddress());

        verify(mockRepo, times(1)).save(updatedCustomer);
    }
    @Test
    public void testUpdateCustomerToDisable() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedbob@yahoo.com";
        String updatedPassword = "disabled";
        String updatedPhone = "+1(514)7654321";

        String email="abcdefghij@yahoo.com";
        Person aPerson= new Person(VALID_NAME, email,VALID_PASSWORD, VALID_PHONE);

        Customer existingCustomer=new Customer(aPerson,VALID_ADDRESS);

        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(existingCustomer);
        // Mock the save method to return the updated person when save() is called
        when(mockRepo.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> {
            Customer updatedCustomer = iom.getArgument(0);
            updatedCustomer.getPerson().setUsername(updatedName);
            updatedCustomer.getPerson().setEmail(updatedEmail);
            updatedCustomer.getPerson().setPassword(updatedPassword);
            updatedCustomer.getPerson().setPhone(updatedPhone);
            updatedCustomer.setShippingAddress("123 Sherbrooke East");

            return updatedCustomer;
        });

        // Act
        Customer updatedCustomer = service.updateCustomer(ID, updatedName,updatedEmail,updatedPassword,updatedPhone,"123 Sherbrooke East");

        // Assert
        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getPerson().getUsername());
        assertEquals(updatedEmail, updatedCustomer.getPerson().getEmail());
        assertEquals("disabled", updatedCustomer.getPerson().getPassword());
        assertEquals(updatedPhone, updatedCustomer.getPerson().getPhone());
        assertEquals("123 Sherbrooke East", updatedCustomer.getShippingAddress());

        verify(mockRepo, times(1)).save(updatedCustomer);
    }


    @Test
    public void testUpdateCustomerByInvalidPasswordLength() {
        // Arrange
        String updatedName = "UpdatedBob";
        String updatedEmail = "updatedbo@hotmail.com";
        String updatedPassword = "newpas";
        String updatedPhone = "+1(514)7654321";
        String updatedAddress="123 Sherbrooke East";
        String email="abcdefghi@hotmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Customer existingCustomer=new Customer(aPerson,VALID_ADDRESS);

        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(existingCustomer);
        // Mock the save method to return the updated person when save() is called

        Person updatedPerson= new Person(updatedName,updatedEmail,updatedPassword,updatedPhone);

        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateCustomer(ID, updatedName,updatedEmail,updatedPassword,updatedPhone,updatedAddress));
        assertEquals(HttpStatus.LENGTH_REQUIRED, ex.getStatus());
        assertEquals("Password needs to be at least 10 characters long", ex.getMessage());
    }
    @Test
    public void testUpdateCustomerByNullUsername() {
        // Arrange
        String updatedName = null;
        String updatedEmail = "updatedb@hotmail.com";
        String updatedPassword = "123456789000";
        String updatedPhone = "+1(514)7654321";
        String updatedAddress="123 Sherbrooke East";
        String email="abcdefgh@hotmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Customer existingCustomer=new Customer(aPerson,VALID_ADDRESS);

        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(existingCustomer);
        // Mock the save method to return the updated person when save() is called



        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateCustomer(ID, updatedName,updatedEmail,updatedPassword,updatedPhone,updatedAddress));


        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Username can not be null", ex.getMessage());


    }
    @Test
    public void testUpdateCustomerByNullPhone() {
        // Arrange
        String updatedName = "Joseph";
        String updatedEmail = "updated@hotmail.com";
        String updatedPassword = "123456789000";
        String updatedPhone = null;
        String updatedAddress="123 Sherbrooke East";
        String email="abcdefg@hotmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Customer existingCustomer=new Customer(aPerson,VALID_ADDRESS);

        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(existingCustomer);
        // Mock the save method to return the updated person when save() is called



        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateCustomer(ID, updatedName,updatedEmail,updatedPassword,updatedPhone,updatedAddress));



        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Phone number can not be null", ex.getMessage());
    }
    @Test
    public void testUpdateCustomerByNullEmail() {
        // Arrange
        String updatedName = "Joseph";
        String updatedEmail = null;
        String updatedPassword = "123456789000";
        String updatedPhone = "+1(514)7654321";
        String updatedAddress="123 Sherbrooke East";
        String email="abcdef@hotmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Customer existingCustomer=new Customer(aPerson,VALID_ADDRESS);

        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(existingCustomer);
        // Mock the save method to return the updated person when save() is called



        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateCustomer(ID, updatedName,updatedEmail,updatedPassword,updatedPhone,updatedAddress));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Email can not be null", ex.getMessage());
    }
    @Test
    public void testUpdateCustomerByNullAddress() {
        // Arrange
        String updatedName = "Joseph";
        String updatedEmail = "update@hotmail.com";
        String updatedPassword = "123456789000";
        String updatedPhone = "+1(514)7654321";
        String updatedAddress=null;
        String email="abcde@hotmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Customer existingCustomer=new Customer(aPerson,VALID_ADDRESS);

        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(existingCustomer);
        // Mock the save method to return the updated person when save() is called



        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateCustomer(ID, VALID_NAME, email, VALID_PASSWORD, VALID_PHONE,updatedAddress));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Shipping address can not be null", ex.getMessage());
    }
    @Test
    public void testUpdateCustomerByInvalidId() {
        // Arrange
        // Act
        // Assert
        String email="abcd@hotmail.com";


        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.updateCustomer(ID, VALID_NAME, email, VALID_PASSWORD, VALID_PHONE,VALID_ADDRESS));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Customer with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testDeleteManagerByValidId() {
        // Arrange
        String email="abcd@hotmail.com";
        Person aPerson = new Person(VALID_NAME, email, VALID_PASSWORD, VALID_PHONE);
        Customer existingCustomer= new Customer(aPerson,VALID_ADDRESS);

        when(mockRepo.findCustomerByRoleId(ID)).thenReturn(existingCustomer);

        // Act
        service.deleteCustomer(ID);

        // Assert
        verify(mockRepo, times(1)).delete(existingCustomer);
    }

    @Test
    public void testDeleteManagerByInvalidId() {
        // Arrange
        // Act
        // Assert
        GameShopException ex = assertThrows(GameShopException.class,
                () -> service.deleteCustomer(ID));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Customer with ID " + ID + " does not exist.", ex.getMessage());
    }
}