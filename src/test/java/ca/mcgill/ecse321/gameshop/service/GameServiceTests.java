package ca.mcgill.ecse321.gameshop.service;


import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.*;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Maissa
 * Test for creating, getting, updating and deleting valid and invalid games.
 */
@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GameServiceTests {
    @Mock
    private GameRepository repo;
    @Mock
    private CategoryRepository catRepo;
    @Mock
    private ReviewRepository reviewRepo;
    @InjectMocks
    private GameService service;

    private String name = "R6";
    private int ID =3;
    private String description="Great game";
    private float price=29;
    private int stock=5;
    private String photo="image";
    private boolean tobeAdded=true;
    private Category category1 = new Category("Action");

    String newName ="Minecraft";
    String newDescription="Good game";
    float newPrice=30;
    int newStock=6;
    String newPhoto="new image";
    boolean tobeRemoved =false;
    boolean tobeAddedNew= false;
    float promotion=20;

    List<Category> categories=List.of(category1);
    List<Integer> categoryIDs= List.of(category1.getCategoryId());;


    @Test

    public void testAddGame(){
        when(repo.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        when(catRepo.findCategoryByCategoryId(category1.getCategoryId())).thenReturn(category1);
        Game createdGame = service.addGame(name,description,price,stock,photo,categoryIDs);

        assertNotNull(createdGame);
        assertEquals(name,createdGame.getName());
        assertEquals(description,createdGame.getDescription());
        assertEquals(price,createdGame.getPrice());
        assertEquals(stock,createdGame.getStockQuantity());
        assertEquals(true,createdGame.getToBeAdded());
        assertEquals(categories,createdGame.getCategories());
        verify(repo,times(1)).save(createdGame);

    }
    @Test
    public void testAddGameByInvalidName(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(null,description,price,stock,photo,categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Name cannot be empty.", ex.getMessage());
    }
    @Test
    public void testAddGameByInvalidDescription(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,null,price,stock,photo,categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Description cannot be empty.", ex.getMessage());
    }

    @Test
    public void testAddGameByInvalidPrice(){

        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,description,-1,stock,photo,categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Price must be over 0.0.", ex.getMessage());
    }

    @Test
    public void testAddGameByInvalidStock(){

        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,description,price,-1,photo,categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Stock quantity must be over 0.0.", ex.getMessage());
    }

    @Test
    public void testAddGameByInvalidPhoto(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,description,price,stock,null,categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Game must have a photo.", ex.getMessage());
    }
    @Test
    public void testAddGameByInvalidCategory(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,description,price,stock,photo,null));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Game must have at least one category.", ex.getMessage());
    }

    @Test
    public void testUpdateValidGame(){

        when(catRepo.findCategoryByCategoryId(category1.getCategoryId())).thenReturn(category1);

        Game g = new Game(name,description,price,stock,photo,categories);

        when(repo.findGameByGameId(ID)).thenReturn(g);
        when(repo.save(any(Game.class))).thenAnswer((InvocationOnMock iom)-> iom.getArgument(0));
        Game updatedGame = service.updateGame(ID,newName,newDescription,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoryIDs);

        assertNotNull(updatedGame);
        assertEquals(newName,updatedGame.getName());
        assertEquals(newDescription,updatedGame.getDescription());
        assertEquals(newPrice,updatedGame.getPrice());
        assertEquals(newStock,updatedGame.getStockQuantity());
        assertEquals(newPhoto,updatedGame.getPhotoURL());
        assertEquals(tobeRemoved,updatedGame.getToBeRemoved());
        assertEquals(tobeAddedNew,updatedGame.getToBeAdded());
        assertEquals(categories,updatedGame.getCategories());
        assertEquals(promotion,updatedGame.getPromotion());

        verify(repo,times(1)).save(updatedGame);
    }

    @Test
    public void testUpdateInvalidGame(){

        when(repo.findGameByGameId(ID)).thenReturn(null);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Game with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testUpdateGameByInvalidName(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,null,newDescription,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Name cannot be empty.", ex.getMessage());
    }
    @Test
    public void testUpdateGameByInvalidDescription(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,null,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Description cannot be empty.", ex.getMessage());
    }

    @Test
    public void testUpdateGameByInvalidPrice(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,-1,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Price must be over 0.0.", ex.getMessage());
    }

    @Test
    public void testUpdateGameByInvalidStock(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,newPrice,0,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoryIDs));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Stock quantity must be over 0.0.", ex.getMessage());
    }

    @Test
    public void testUpdateGameByInvalidPhoto(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,newPrice,newStock,null,tobeAddedNew, tobeRemoved, promotion, categoryIDs));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Game must have a photo.", ex.getMessage());
    }
    @Test
    public void testUpdateGameByInvalidCategory(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, null));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Game must have at least one category.", ex.getMessage());
    }

    @Test
    public void testGetAllGames(){
        String newName ="Minecraft";
        String newDescription="Good game";
        float newPrice=30;
        int newStock=6;
        String newPhoto="new image";
        boolean tobeAddedNew= false;
        Category categoriesNew = new Category("Action");

        Iterable<Game> games = List.of(new Game(name,description,price,stock,photo,categories),
                new Game(newName,newDescription,newPrice,newStock,newPhoto,categories));
        when(repo.findAll()).thenReturn(games);

        Iterable<Game> foundGames = service.getAllGames();

        assertNotNull(foundGames);
        assertEquals(games, foundGames);
        verify(repo, times(1)).findAll();
    }

    @Test
    public void testGetGameByID(){
        when(repo.findGameByGameId(ID)).thenReturn(new Game(name,description,price,stock,photo));

        Game foundGame = service.getGame(ID);

        assertNotNull(foundGame);
        assertEquals(name,foundGame.getName());
        assertEquals(description,foundGame.getDescription());
        assertEquals(price,foundGame.getPrice());
        assertEquals(stock,foundGame.getStockQuantity());
    }

    @Test
    public void testGetGameByInvalidID(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.getGame(-1));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The Game ID "+ -1 +"is not valid", ex.getMessage());
    }

    @Test
    public void testGetNonExistentGame(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.getGame(ID));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Game with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testGetGameByName(){
        when(repo.findAll()).thenReturn(List.of(new Game(name,description,price,stock,photo)));
        Game foundGame= service.getGameByName(name);

        assertNotNull(foundGame);
        assertNotNull(foundGame);
        assertEquals(name,foundGame.getName());
        assertEquals(description,foundGame.getDescription());
        assertEquals(price,foundGame.getPrice());
        assertEquals(stock,foundGame.getStockQuantity());
    }

    @Test
    public void testGetGamesByCategory(){
        Game c=new Game(name,description,price,stock,photo,categories);

        when(repo.findAll()).thenReturn(List.of(c));

        List<Game> games = service.getGamesByCategory(category1.getCategoryName());

        List<Game> result = new ArrayList<>();
        result.add(c);

        assertNotNull(games);
        assertEquals(result,games);
    }

    @Test
    public void testDeleteValidGame() {
        Game g=new Game(name, description, price, stock, photo, categories);
        g.setToBeRemoved(true);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        service.deleteGame(ID);
        verify(repo, times(1)).deleteById(ID);
    }
    @Test
    public void testDeleteNonApprovedGame() {
        Game g=new Game(name, description, price, stock, photo, categories);
        g.setToBeRemoved(false);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.deleteGame(ID));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("This game is not authorized to be deleted",ex.getMessage());
    }

    @Test
    public void testApprovalToAddValidGame(){
        when(repo.findGameByGameId(ID)).thenReturn(new Game(name, description, price, stock, photo, categories));
        Boolean approved = service.approvalToAddGame(ID);

        assertEquals(tobeAdded,approved);

    }
    @Test
    public void testApprovalToAddInvalidGame(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.approvalToAddGame(ID));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The game does not exist in the database",ex.getMessage());
    }
    @Test
    public void testApprovalToRemoveValidGame(){
        when(repo.findGameByGameId(ID)).thenReturn(new Game(name, description, price, stock, photo, categories));
        Boolean approved = service.approvalToRemoveGame(ID);

        assertEquals(false,approved);

    }
    @Test
    public void testApprovalToRemoveInvalidGame(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.approvalToRemoveGame(ID));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The game does not exist in the database",ex.getMessage());
    }

    /**
     * Test get all customer payment methods
     */
    @Test
    public void testGetGameReviews() {
        // Test Data
        Review.StarRating rating1 = Review.StarRating.FiveStar;
        Review.StarRating rating2 = Review.StarRating.FourStar;
        Review.StarRating rating3 = Review.StarRating.TwoStar;

        String comment1 = "Amazing game!";
        String comment2 = "Great graphics!";
        String comment3 = "Not my type of game.";

        Customer customer = new Customer();
        customer.setRoleId(1); // Setting customer ID

        Game createdGame = new Game(name, description, price, stock, photo, categories);
        createdGame.setGameId(ID); // Setting the game's ID to match the test scenario

        Game otherGame = new Game(newName, newDescription, newPrice, newStock, newPhoto, categories);
        otherGame.setGameId(99); // An unrelated game ID for negative testing

        Review review1 = new Review(rating1, comment1, customer.getRoleId());
        review1.setGame(createdGame);

        Review review2 = new Review(rating2, comment2, customer.getRoleId());
        review2.setGame(createdGame);

        Review review3 = new Review(rating3, comment3, customer.getRoleId());
        review3.setGame(otherGame);

        List<Review> allReviews = new ArrayList<>(List.of(review1, review2, review3));

        // Mocking Behavior
        when(repo.findGameByGameId(ID)).thenReturn(createdGame);
        when(reviewRepo.findAll()).thenReturn(allReviews);

        // Act
        List<Review> gameReviews = service.getGameReviews(ID);

        // Assert
        assertNotNull(gameReviews);
        assertEquals(2, gameReviews.size()); // Only reviews linked to the specified game
        assertTrue(gameReviews.contains(review1));
        assertTrue(gameReviews.contains(review2));
        assertFalse(gameReviews.contains(review3)); // Reviews for other games should not be included

        verify(repo, times(1)).findGameByGameId(ID);
        verify(reviewRepo, times(1)).findAll();
    }


}
