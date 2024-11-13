package ca.mcgill.ecse321.gameshop.service;


import java.util.ArrayList;
import java.util.Arrays;
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
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GameServiceTests {
    @Mock
    private GameRepository repo;
    @InjectMocks
    private GameService service;
    private String name = "R6";
    private int ID =3;
    private String description="Great game";
    private float price=29;
    private int stock=5;
    private String photo="image";
    private boolean tobeAdded=true;
    private List<Category> categories = new ArrayList<>(Arrays.asList(
        new Category("Action"),
        new Category("Adventure"),
        new Category("Puzzle")
    ));
    String newName ="Minecraft";
    String newDescription="Good game";
    float newPrice=30;
    int newStock=6;
    String newPhoto="new image";
    boolean tobeRemoved =false;
    boolean tobeAddedNew= false;
    List<Category> categoriesNew = new ArrayList<>(Arrays.asList(
        new Category("Action"),
        new Category("Adventure"),
        new Category("Puzzle"),
        new Category("Crime")
    ));
    float promotion=20;
    ArrayList<Category> categoryList = new ArrayList<>();


    @Test
    public void testAddGame(){
        when(repo.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        Game createdGame = service.addGame(name,description,price,stock,photo,categories);

        assertNotNull(createdGame);
        assertEquals(name,createdGame.getName());
        assertEquals(description,createdGame.getDescription());
        assertEquals(price,createdGame.getPrice());
        assertEquals(stock,createdGame.getStockQuantity());
        assertEquals(true,createdGame.getToBeAdded());
        verify(repo,times(1)).save(createdGame);

    }
    @Test
    public void testAddGameByInvalidName(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(null,description,price,stock,photo,categories));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Name cannot be empty.", ex.getMessage());
    }
    @Test
    public void testAddGameByInvalidDescription(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,null,price,stock,photo,categories));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Description cannot be empty.", ex.getMessage());
    }

    @Test
    public void testAddGameByInvalidPrice(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,description,-1,stock,photo,categories));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Price must be over 0.0.", ex.getMessage());
    }

    @Test
    public void testAddGameByInvalidStock(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,description,price,-1,photo,categories));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Stock quantity must be over 0.0.", ex.getMessage());
    }

    @Test
    public void testAddGameByInvalidPhoto(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.addGame(name,description,price,stock,null,categories));

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
        Category newOne = new Category("Sports");
        categoryList.add(newOne);

        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        when(repo.save(any(Game.class))).thenAnswer((InvocationOnMock iom)-> iom.getArgument(0));
        Game updatedGame = service.updateGame(ID,newName,newDescription,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoriesNew);

        assertNotNull(updatedGame);
        assertEquals(newName,updatedGame.getName());
        assertEquals(newDescription,updatedGame.getDescription());
        assertEquals(newPrice,updatedGame.getPrice());
        assertEquals(newStock,updatedGame.getStockQuantity());
        assertEquals(newPhoto,updatedGame.getPhotoURL());
        assertEquals(tobeRemoved,updatedGame.getToBeRemoved());
        assertEquals(tobeAddedNew,updatedGame.getToBeAdded());
        assertEquals(categoryList,updatedGame.getCategories());
        assertEquals(promotion,updatedGame.getPromotion());

        verify(repo,times(1)).save(updatedGame);
    }

    @Test
    public void testUpdateInvalidGame(){

        when(repo.findGameByGameId(ID)).thenReturn(null);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoriesNew));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Game with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testUpdateGameByInvalidName(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,null,newDescription,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoriesNew));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Name cannot be empty.", ex.getMessage());
    }
    @Test
    public void testUpdateGameByInvalidDescription(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,null,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoriesNew));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Description cannot be empty.", ex.getMessage());
    }

    @Test
    public void testUpdateGameByInvalidPrice(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,-1,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoriesNew));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Price must be over 0.0.", ex.getMessage());
    }

    @Test
    public void testUpdateGameByInvalidStock(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,newPrice,0,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoriesNew));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Stock quantity must be over 0.0.", ex.getMessage());
    }

    @Test
    public void testUpdateGameByInvalidPhoto(){
        Game g = new Game(name,description,price,stock,photo,categories);
        when(repo.findGameByGameId(ID)).thenReturn(g);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,newPrice,newStock,null,tobeAddedNew, tobeRemoved, promotion, categoriesNew));
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
        Iterable<Game> games = List.of(new Game(name,description,price,stock,photo,categories),
                new Game(newName,newDescription,newPrice,newStock,newPhoto,categoriesNew));
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
        assertEquals("The Game ID "+ -1 +"is not valid.", ex.getMessage());
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

        when(repo.findAll()).thenReturn(List.of(c,new Game(name,description,price,stock,photo,categories)));
        List<Game> games = service.getGamesByCategory(categories.get(1));

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

}
