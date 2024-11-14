package ca.mcgill.ecse321.gameshop.service;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Command;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;


@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CommandServiceTests {
    @Mock
    private CommandRepository repo;
    @InjectMocks
    private CommandService service;
    private float total =99;
    private int ID=3;
    private String today = "2024-01-02";
    private Game g1= new Game("R6", "Great game", 49, 6,"URL");
    private Game g2= new Game("Minecraft", "Great game", 50, 6,"URL");

    @Test
    public void testCreateValidCommand(){
        when(repo.save(any(Command.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        Command createdCommand = service.createCommand(today, total);

        assertNotNull(createdCommand);
        assertEquals(g1.getPrice()+g2.getPrice(),createdCommand.getTotalPrice());
        verify(repo,times(1)).save(createdCommand);
    }

    @Test
    public void testCreateInvalidCommand(){
        GameShopException ex= assertThrows(GameShopException.class,()-> service.createCommand(null, -1));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals("Phone number can not be null",ex.getMessage());
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