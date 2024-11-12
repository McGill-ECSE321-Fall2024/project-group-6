package ca.mcgill.ecse321.gameshop.integration;

import ca.mcgill.ecse321.gameshop.dto.CommandRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CommandResponseDto;
import ca.mcgill.ecse321.gameshop.model.Customer;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandIntegrationTests {
    @Autowired
    private TestRestTemplate order;
    @Autowired
    private CommandRepository repo;

    private static int ID;
    private static final Date today= Date.valueOf(LocalDate.now());;

    private final Game g1= new Game("R6", "Great game", 49, 6,"URL");
    private final Game g2= new Game("Minecraft", "Great game", 50, 6,"URL");
    private static final float total = 49+50;
    private final List<Game> cart = new ArrayList<>(List.of(g1,g2));
    private static final List<Game> wishlist = new ArrayList<>();

    private final Customer tim = new Customer(new Person("Tim","Tim@gmail.com","password","438777906"),"4555 milton",wishlist,cart );



    @AfterAll
   public void clearDatabase() {
        repo.deleteAll();
    }


@SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidCommand() {

        // Arrange
        CommandRequestDto command = new CommandRequestDto(tim);

        // Act
        ResponseEntity<CommandResponseDto> response = order.postForEntity("/commands", command, CommandResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getId() > 0, "The ID should be positive.");

        assertEquals(tim, response.getBody().getCustomer());
        assertEquals(total, response.getBody().getTotal());
        assertEquals(today, response.getBody().getCommandDate());
    }
    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testGetValidCommandById() {
        // Arrange
        String url = String.format("/commands/%d", this.ID);

        System.out.println(String.format("URL: %s", url));

        // Act
        ResponseEntity<CommandResponseDto> response = order.getForEntity(url, CommandResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.ID, response.getBody().getId());
        assertEquals(tim, response.getBody().getCustomer());
        assertEquals(total, response.getBody().getTotal());
        assertEquals(today, response.getBody().getCommandDate());
    }
    @Test
    @Order(3)
    public void testGetCommandByInvalidId() {
        // Arrange
        String url = String.format("/commands/%d", -1);

        // Act
        ResponseEntity<CommandResponseDto> response = order.getForEntity(url, CommandResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteCommandByValidId() {
        // Arrange
        String url = String.format("/commands/%d", this.ID);

        // Act
        ResponseEntity<Void> response = order.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<CommandResponseDto> deletedCommand = order.getForEntity(url, CommandResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedCommand.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeleteCategoryByInvalidId() {
        // Arrange
        String url = String.format("/commands/%d", -1);

        // Act
        ResponseEntity<Void> response = order.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
