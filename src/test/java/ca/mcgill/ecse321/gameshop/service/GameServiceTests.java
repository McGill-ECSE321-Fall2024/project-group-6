package ca.mcgill.ecse321.gameshop.service;


import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private Category categories=new Category("Action");

    @Test
    public void testAddGame(){
        when(repo.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        Game createdGame = service.addGame(name,description,price,stock,photo,tobeAdded,categories);

        assertNotNull(createdGame);
        assertEquals(name,createdGame.getName());
        assertEquals(description,createdGame.getDescription());
        assertEquals(price,createdGame.getPrice());
        assertEquals(stock,createdGame.getStockQuantity());
        assertEquals(true,createdGame.getToBeAdded());
        verify(repo,times(1)).save(createdGame);

    }

    @Test
    public void testAddGameByEmployee(){
        when(repo.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        Game createdGame = service.addGameByEmployee(name,description,price,stock,photo,tobeAdded,categories);

        assertNotNull(createdGame);
        assertEquals(name,createdGame.getName());
        assertEquals(description,createdGame.getDescription());
        assertEquals(price,createdGame.getPrice());
        assertEquals(stock,createdGame.getStockQuantity());
        assertEquals(false,createdGame.getToBeAdded());
        verify(repo,times(1)).save(createdGame);
    }

    @Test
    public void testUpdateValidGame(){
        String newName ="Minecraft";
        String newDescription="Good game";
        float newPrice=30;
        int newStock=6;
        String newPhoto="new image";
        boolean tobeRemoved =false;
        boolean tobeAddedNew= false;
        Category categoriesNew = new Category("Sports");
        float promotion=20;
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(categoriesNew);


        Game g = new Game(name,description,price,stock,photo,tobeAdded,categories);
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
        String newName ="Minecraft";
        String newDescription="Good game";
        float newPrice=30;
        int newStock=6;
        String newPhoto="new image";
        boolean tobeRemoved =false;
        boolean tobeAddedNew= false;
        Category categoriesNew = new Category("Action");
        float promotion=20;

        when(repo.findGameByGameId(ID)).thenReturn(null);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateGame(ID,newName,newDescription,newPrice,newStock,newPhoto,tobeAddedNew, tobeRemoved, promotion, categoriesNew));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Game with ID " + ID + " does not exist.", ex.getMessage());

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

        Iterable<Game> games = List.of(new Game(name,description,price,stock,photo,tobeAdded,categories),
                new Game(newName,newDescription,newPrice,newStock,newPhoto,tobeAddedNew,categoriesNew));
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
    public void testDeleteValidGame() {
        //when(repo.findGameByGameId(ID)).thenReturn(new Game(name, description, price, stock, photo, tobeAdded, categories));
        service.deleteGame(ID);
        verify(repo, times(1)).deleteById(ID);
    }

    @Test
    public void testApprovalToAddValidGame(){
        when(repo.findGameByGameId(ID)).thenReturn(new Game(name, description, price, stock, photo, tobeAdded, categories));
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
        when(repo.findGameByGameId(ID)).thenReturn(new Game(name, description, price, stock, photo, tobeAdded, categories));
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
