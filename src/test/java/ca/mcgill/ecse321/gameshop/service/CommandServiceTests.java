package ca.mcgill.ecse321.gameshop.service;


import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CommandServiceTests {
    @Mock
    private CommandRepository repo;
    @InjectMocks
    private CommandService service;
    private float total =10;
    private int ID=3;
    private Date today= Date.valueOf(LocalDate.now());

    @Test
    public void testCreateValidCommand(){
        when(repo.save(any(Command.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        Command createdCommand = service.createCommand(total);

        assertNotNull(createdCommand);
        assertEquals(total,createdCommand.getTotalPrice());
        verify(repo,times(1)).save(createdCommand);
    }

    @Test
    public void testCreateInvalidCommand(){
        GameShopException ex= assertThrows(GameShopException.class,()-> service.createCommand(-1));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Command total must be larger than 0.0",ex.getMessage());
    }

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
