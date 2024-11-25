package ca.mcgill.ecse321.gameshop.integration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gameshop.dto.CommandListDto;
import ca.mcgill.ecse321.gameshop.dto.CommandRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CommandResponseDto;
import ca.mcgill.ecse321.gameshop.repository.CommandRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandIntegrationTests {
    @Autowired
    private TestRestTemplate order;
    @Autowired
    private CommandRepository repo;

    private static int ID;
    private static final String date = "2004-01-02";
    private static final float total = 0;

    @AfterAll
    public void clearDatabase() {
        repo.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    @Order(1)
    public void testCreateValidCommand() {
        // Arrange
        CommandRequestDto command = new CommandRequestDto(total, date);

        // Act
        ResponseEntity<CommandResponseDto> response = order.postForEntity("/command", command, CommandResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getCommandId() > 0, "The ID should be positive.");
        ID = response.getBody().getCommandId();
        assertEquals(total, response.getBody().getTotal());
        assertEquals(date, response.getBody().getCommandDate());
    }

    @Test
    @Order(2)
    public void testGetAllPeople() {
        // Arrange
        // Act
        ResponseEntity<CommandListDto> response = order.getForEntity("/command", CommandListDto.class);
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

        System.out.println(String.format("URL: %s", url));

        // Act
        ResponseEntity<CommandResponseDto> response = order.getForEntity(url, CommandResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.ID, response.getBody().getCommandId());
        assertEquals(total, response.getBody().getTotal());
        assertEquals(date, response.getBody().getCommandDate());
    }

    @Test
    @Order(4)
    public void testGetCommandByInvalidId() {
        // Arrange
        String url = String.format("/command/%d", -1);

        // Act
        ResponseEntity<CommandResponseDto> response = order.getForEntity(url, CommandResponseDto.class);

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
        ResponseEntity<Void> response = order.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<CommandResponseDto> deletedCommand = order.getForEntity(url, CommandResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedCommand.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteCategoryByInvalidId() {
        // Arrange
        String url = String.format("/command/%d", -1);

        // Act
        ResponseEntity<Void> response = order.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
