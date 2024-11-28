package ca.mcgill.ecse321.gameshop.service;


import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;
import ca.mcgill.ecse321.gameshop.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Maissa
 */
@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CommandServiceTests {
    @Mock
    private CommandRepository repo;
    @Mock
    private CustomerRepository customerRepo;
    @InjectMocks
    private CommandService service;
    private float total =99;
    private int ID=3;
    private String today = "2024-01-02";
    private Game g1= new Game("R6", "Great game", 49, 6,"URL");
    private Game g2= new Game("Minecraft", "Great game", 50, 6,"URL");
    private final List<Game> cart = new ArrayList<>(List.of(g1,g2));
    private static final List<Game> wishlist = new ArrayList<>();
    private final Customer tim = new Customer(new Person("Tim","Tim@gmail.com","password","438777906"),"4555 milton",wishlist,cart );
    private final int timID=2;
    @Test
    public void testCreateValidCommand(){
        when(repo.save(any(Command.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(customerRepo.findCustomerByRoleId(timID)).thenReturn(tim);
        Command createdCommand = service.createCommand(timID);

        assertNotNull(createdCommand);
        assertEquals(g1.getPrice()+g2.getPrice(),createdCommand.getTotalPrice());
        verify(repo,times(1)).save(createdCommand);
    }

    @Test
    public void testCreateInvalidCommand(){
        GameShopException ex= assertThrows(GameShopException.class,()-> service.createCommand(-1));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Command must belong to a customer.",ex.getMessage());    }

    @Test
    public void testGetCommandByValidID() {
        when(repo.findCommandByCommandId(ID)).thenReturn(new Command(today.toString(),total));
        Command c = service.findCommandById(ID);

        assertNotNull(c);
        assertEquals(today.toString(),c.getCommandDate());
        assertEquals(total,c.getTotalPrice());
    }

    @Test
    public void testGetCommandByInvalidID(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.findCommandById(-1));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The Command ID "+ -1 +"is not valid", ex.getMessage());
    }

    @Test
    public void testGetCommandByNonExistentId(){
        when(repo.findCommandByCommandId(ID)).thenReturn(null);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.findCommandById(ID));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("There is no Command with ID "+ ID+".", ex.getMessage());
    }

    @Test
    public void testDeleteValidCommand(){
        when(repo.findCommandByCommandId(ID)).thenReturn(new Command(today.toString(),total));
        service.deleteCommand(ID);

        verify(repo, times(1)).findCommandByCommandId(ID);
        verify(repo, times(1)).deleteById(ID);

    }

    @Test
    public void testDeleteInvalidCommand(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.deleteCommand(-1));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The Command ID "+ -1 +"is not valid", ex.getMessage());
    }

    @Test
    public void testDeleteNonExistentCategory(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.deleteCommand(ID));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("There is no Command with ID "+ ID+".", ex.getMessage());
    }

    @Test
    public void testGetAllCommands(){
        Iterable<Command> commands = List.of(new Command(today.toString(),total), new Command(today.toString(),20));
        when(repo.findAll()).thenReturn(commands);

        Iterable<Command> foundCommands = service.getAllCommands();

        assertNotNull(foundCommands);
        assertEquals(commands, foundCommands);
        verify(repo, times(1)).findAll();
    }




}