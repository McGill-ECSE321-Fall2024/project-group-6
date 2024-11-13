package ca.mcgill.ecse321.gameshop.integration;

/**
 * @author Joseph and Maissa
 */
import ca.mcgill.ecse321.gameshop.dto.CategoryRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CategoryResponseDto;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)


public class CategoryIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CategoryRepository repo;


    private static final String name = "Action";
    private int ID;


    @AfterAll
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidCategory() {

        // Arrange
        CategoryRequestDto category = new CategoryRequestDto(name);


        // Act
        ResponseEntity<CategoryResponseDto> response = client.postForEntity("/categories", category, CategoryResponseDto.class);


        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getId() > 0, "The ID should be positive.");
        this.ID=response.getBody().getId();
        assertEquals(name, response.getBody().getName());
    }

    @Test
    @Order(2)
    public void testGetValidCategoryById() {
        // Arrange
        String url = String.format("/categories/%d", this.ID);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.ID, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
    }
    @Test
    @Order(3)
    public void testGetCategoryByInvalidId() {
        // Arrange
        String url = String.format("/categories/%d", -1);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(4)
    public void testUpdateCategoryByValidId() {
        // Arrange
        String newName = "Sports";
        //CategoryRequestDto updatedCategoryDto = new CategoryRequestDto(newName);
        String url = String.format("/categories/%d", this.ID);

        // Act
        client.put(url, newName);

        // Fetch updated person
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newName, response.getBody().getName());
    }
    @Test
    @Order(5)
    public void testUpdateCategoryByInvalidId() {
        // Arrange
        String newName = "Sports";
        String url = String.format("/categories/%d", -1);
        CategoryRequestDto updatedCategoryDto = new CategoryRequestDto(newName);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.exchange(url, HttpMethod.PUT, new HttpEntity<>(updatedCategoryDto), CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteCategoryByValidId() {
        // Arrange
        String url = String.format("/categories/%d", this.ID);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the person was actually deleted by trying to fetch it again
        ResponseEntity<CategoryResponseDto> deletedCategory = client.getForEntity(url, CategoryResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, deletedCategory.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeleteCategoryByInvalidId() {
        // Arrange
        String url = String.format("/categories/%d", -1);

        // Act
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }





}