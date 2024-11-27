
package ca.mcgill.ecse321.gameshop.integration;

import ca.mcgill.ecse321.gameshop.dto.CommandListDto;
import ca.mcgill.ecse321.gameshop.dto.CommandResponseDto;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Mario and Maissa
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CommandRepository repo;
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private CustomerRepository customerRepo;


    private static int ID;
    private static final Date date= Date.valueOf(LocalDate.now());;
    private static final float total = 49+50;
    private Customer tim ;
    private int timID;



    private static final List<Category> categories = new ArrayList<>();
    private final List<Game> cart = new ArrayList<>();
    private static final List<Game> wishlist = new ArrayList<>();


    @AfterAll
    public void clearDatabase() {
        customerRepo.deleteAll();
        personRepo.deleteAll();
        gameRepo.deleteAll();
        categoryRepo.deleteAll();
        repo.deleteAll();
    }

    @BeforeAll
    public void setup() {
        // Creating category for game
        Category c1 = new Category("FPS");
        categoryRepo.save(c1);
        this.categories.add(c1);

        // creating games to add to customer's cart
        Game g1= new Game("R6", "Great game", 49, 6,"URL",categories);
        Game g2= new Game("Minecraft", "Great game", 50, 6,"URL",categories);
        gameRepo.save(g1);
        gameRepo.save(g2);
        Person timPerson= new Person("Tim","Tim@gmail.com","password","438777906");
        personRepo.save(timPerson);
        cart.add(g1);
        cart.add(g2);

        // creating customer
        this.tim = new Customer(timPerson,"4555 milton",wishlist,cart );
        customerRepo.save(tim);
        this.timID=tim.getRoleId();

    }
    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidCommand() {
        // Arrange

        //CommandRequestDto command = new CommandRequestDto(timID);
        String url = String.format("/command/%d", timID);
        // Act
        ResponseEntity<CommandResponseDto> response = client.postForEntity(url,timID,CommandResponseDto.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getCommandId() > 0, "The ID should be positive.");
        ID = response.getBody().getCommandId();
        assertEquals(total, response.getBody().getTotalPrice());
        assertEquals(date.toString(), response.getBody().getCommandDate());
    }

    @Test
    @Order(2)
    public void testGetAllPeople() {
        // Arrange
        // Act
        ResponseEntity<CommandListDto> response = client.getForEntity("/command", CommandListDto.class);
        CommandListDto commands = response.getBody();

        // Assert
        assertNotNull(commands);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(commands.getCommands());
        assertTrue(!commands.getCommands().isEmpty(), "There should be at least 1 command returned.");
    }

    @SuppressWarnings("null")
    @Test
    @Order(3)
    public void testGetCommandById() {
        // Arrange
        String url = String.format("/command/%d", ID);

        // Act
        ResponseEntity<CommandResponseDto> response = client.getForEntity(url, CommandResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.ID, response.getBody().getCommandId());
        assertEquals(total, response.getBody().getTotalPrice());
        assertEquals(date.toString(), response.getBody().getCommandDate());
    }

    @Test
    @Order(4)
    public void testGetCommandByInvalidId() {
        // Arrange
        String url = String.format("/command/%d", -1);

        // Act
        ResponseEntity<CommandResponseDto> response = client.getForEntity(url, CommandResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testDeleteCommandByValidId() {
        // Arrange
        String url = String.format("/command/%d", ID);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<CommandResponseDto> deletedCommand = client.getForEntity(url, CommandResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedCommand.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteCategoryByInvalidId() {
        // Arrange
        String url = String.format("/command/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
