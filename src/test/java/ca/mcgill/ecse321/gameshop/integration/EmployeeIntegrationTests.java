package ca.mcgill.ecse321.gameshop.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gameshop.GameshopApplication;
import ca.mcgill.ecse321.gameshop.repository.*;
import ca.mcgill.ecse321.gameshop.dto.*;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.service.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GameshopApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private GameService gameService;

    private int employeeId;
    private int managerId;
    private int gameId;
    private static final String VALID_USERNAME = "employee1";
    private static final String VALID_PASSWORD = "password123";
    private static final String VALID_EMAIL = "employee1@gmail.com";
    private static final String VALID_PHONE = "4912561548";
    private static final String VALID_MANAGER_USERNAME = "manager1";
    private static final String VALID_MANAGER_PASSWORD = "managerpassword";
    private static final String VALID_MANAGER_EMAIL = "manager@gmail.com";
    private static final String VALID_MANAGER_PHONE = "819987954";

    @AfterAll
    public void clearDatabase() {
        employeeService.deleteAll();
        gameService.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidEmployee() {
        // Arrange
        Manager manager = new Manager(new Person(VALID_MANAGER_USERNAME, VALID_MANAGER_EMAIL, VALID_MANAGER_PASSWORD, VALID_MANAGER_PHONE));
        managerService.createManager(manager);

        // Act
        Employee employee = new Employee(new Person(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD, VALID_PHONE),null, true);
        employeeService.addEmployee(employee);

        // Assert
        assertNotNull(employee);
        this.employeeId = employee.getId();
        this.managerId = manager.getId();
    }

    @Test
    @Order(2)
    public void testAddGameAfterManagerApproval() {
        // Arrange
        Manager manager = managerService.getManagerById(this.managerId);
        Game game = new Game("New Game", "boardgame", 20, 10, "www.photo.ca");

        // Simulate manager approval for adding game
        boolean isApproved = manager.approvalToAddGame(gameId);
        assertTrue(isApproved);

        // Act
        gameService.addGameByEmployee(game, employeeService.getEmployeeById(this.employeeId));

        // Assert
        Game addedGame = gameService.getGameById(game.getId());
        assertNotNull(addedGame);
        assertEquals("New Game", addedGame.getName());
    }

    @Test
    @Order(3)
    public void testRemoveGameAfterManagerApproval() {
        // Arrange
        Manager manager = managerService.getManagerById(this.managerId);
        Game game = new Game();
        gameService.addGame(game);

        // Simulate manager approval for removing game
        boolean isApproved = manager.approvalToRemoveGame(gameId);
        assertTrue(isApproved);

        // Act
        employeeService.removeGame(gameId);

        // Assert
        Game removedGame = gameService.getGameById(game.getId());
        assertEquals(null, removedGame);  // Game should be removed from the catalog
    }

    @Test
    @Order(4)
    public void testAddGameToCatalog() {
        // Arrange
        Game game = new Game();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer validToken");

        HttpEntity<Game> request = new HttpEntity<>(game, headers);

        // Act
        ResponseEntity<Game> response = client.exchange("/employee/{employeeId}/addGame", HttpMethod.POST, request, Game.class, this.employeeId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Action Game", response.getBody().getName());
    }

    @Test
    @Order(5)
    public void testRemoveGameFromCatalog() {
        // Arrange
        Game game = new Game();
        gameService.saveGame(game);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer validToken");

        HttpEntity<Game> request = new HttpEntity<>(game, headers);

        // Act
        ResponseEntity<Void> response = client.exchange("/employee/{employeeId}/removeGame", HttpMethod.POST, request, Void.class, this.employeeId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
