package ca.mcgill.ecse321.gameshop.repository;

import ca.mcgill.ecse321.gameshop.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@SpringBootTest
public class GamePersistenceTest {


    @Autowired
    private GameRepository gameRepo;


    @BeforeEach

    @AfterEach
    public void clearDatabase() {

        gameRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadGame() {

        Game game= new Game("FC 24","Soccer Game", 88.90F,70,"https://www.si.com/.image/t_share/MTk5MzQ1NDU2NDYxOTE2MTEw/erling-haaland-pictured-on-the-front-cover-of-ea-sports-fc-24-video-game.jpg");

        game = gameRepo.save(game);
        Game gameFromDb = gameRepo.findGameByGameId(game.getGameId());

        assertNotNull(gameFromDb);

        assertEquals(gameFromDb.getPrice(),88.90F); //variable test



    }
}
