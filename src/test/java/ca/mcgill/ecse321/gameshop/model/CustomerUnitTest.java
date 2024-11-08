package ca.mcgill.ecse321.gameshop.model;

import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import ca.mcgill.ecse321.gameshop.service.CustomerService;
import org.assertj.core.api.IterableSizeAssert;
import org.assertj.core.internal.Iterables;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerUnitTest {

    @Mock
    private CustomerRepository repo;
    @InjectMocks
    private CustomerService service;

    @Test
    public void testCreateValidCustomer() {
        // Arrange
        Person person = new Person("John Doe","johndoe@gmail.com", "123ABC","8195126548");
        String shippingAddress = "123 Sherbrooke West";
        Customer customer = new Customer(person,shippingAddress);
        when(repo.save(any(Customer.class))).thenReturn(customer);
        // Act
        Customer createdCustomer = service.createCustomer(person,shippingAddress);
        // Assert
        assertNotNull(createdCustomer);
        assertEquals(shippingAddress,createdCustomer.getShippingAddress());
        assertEquals(person,createdCustomer.getPerson());
        verify(repo,times(1)).save(any(Customer.class));
    }

    @Test
    public void testFindCustomerByIdValid() {
        // Arrange
        int id = 1;
        Customer customer = new Customer(new Person("John Doe","johndoe@gmail.com", "123ABC","8195126548"), "123 Sherbrooke West");
        when(repo.findById(id)).thenReturn(Optional.of(customer));
        // Act
        Customer customerFound = service.findCustomerByID(id);
        // Assert
        assertNull(customerFound);
        assertEquals("123 Sherbrooke West", customerFound.getShippingAddress());
        verify(repo,times(1)).findCustomerByRoleId(id);
    }

    @Test
    public void testFindCustomerByIdInvalid() {
        // Arrange
        int id = 2;
        when(repo.findById(id)).thenReturn(Optional.empty());
        // Act
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.findCustomerByID(id));
        assertEquals("Customer with this ID was not found", exception.getMessage());
        verify(repo,times(1)).findById(id);
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        Customer customer1 = new Customer(new Person("John Doe","johndoe@gmail.com", "123ABC","8195126548"), "123 Sherbrooke West");
        Customer customer2 = new Customer(new Person("Jane Doe","janedoe@gmail.com", "ABC123","6135126545"), "123 Sherbrooke East");
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        when(repo.findAll()).thenReturn(customers);
        // Act
        Iterable<Customer> allCostumers = service.getAllCustomers();
        // Assert
        assertNotNull(allCostumers);
        assertEquals(2, Iterables.size(allCostumers));
        verify(repo,times(1)).findAll();
    }

    @Test
    public void testUpdateValidCustomer() {
        // Arrange
        int id = 4;
        Customer existingCustomer = new Customer(new Person("Jane Doe","janedoe@gmail.com", "ABC123","6135126545"), "123 Sherbrooke East");
        Customer updatedDetails = new Customer(new Person("Jamie Doe","jamiedoe@gmail.com","d4g53dfs","45623789"),"45 Aylmer Street");
        when(repo.findById(id)).thenReturn(Optional.of(existingCustomer));
        when(repo.save(any(Customer.class))).thenReturn(updatedDetails);
        // Act
        Customer updatedCustomer = service.updateCustomer(id,updatedDetails);
        // Assert
        assertNotNull(updatedCustomer);
        assertEquals(updatedDetails.getPerson(),updatedCustomer.getPerson());
        assertEquals(updatedDetails.getShippingAddress(), updatedCustomer.getShippingAddress());
        verify(repo,times(1)).findCustomerByRoleId(id);
        verify(repo,times(1)).save(any(Customer.class));

    }

    @Test
    public void testUpdateInvalidCustomer() {
        // Arrange
        int invalidId = 95;
        Customer updatedDetails = new Customer(new Person("Jamie Doe","jamiedoe@gmail.com","d4g53dfs","45623789"),"45 Aylmer Street");
        // Simulate non-existing customer
        when(repo.findCustomerByRoleId(invalidId)).thenReturn(null);
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.updateCustomer(invalidId,updatedDetails));
        assertEquals("Customer with this ID could not be updated:", invalidId, exception.getMessage());
        verify(repo,times(1)).findCustomerByRoleId(invalidId);
        verify(repo,times(0)).save(any(Customer.class));

    }

    @Test
    public void testDeleteCustomer() {
        // Arrange
        int id = 5;
        doNothing().when(repo).deleteById(id);
        // Act
        service.deleteCustomer(id);
        // Assert
        verify(repo,times(1)).deleteById(id);

    }


}
