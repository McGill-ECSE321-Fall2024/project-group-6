package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository repo;
    @InjectMocks
    private CategoryService service;
    private String name = "Action";
    private int ID =3;
    /**
     * @author Maissa
     * Test for creating a valid category.
     */
    @Test
    public void testCreateValidCategory(){

        when(repo.save(any(Category.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        Category createdCategory = service.createCategory(name);

        assertNotNull(createdCategory);
        assertEquals(name,createdCategory.getCategoryName());
        verify(repo,times(1)).save(createdCategory);
    }
    /**
     * @author Maissa
     * Test for handling the creation of a duplicate category.
     */
    @Test
    public void testCreateDuplicateCategory(){

        Iterable<Category> categories = List.of(new Category(name));
        when(repo.findAll()).thenReturn(categories);

        GameShopException ex = assertThrows(GameShopException.class,()-> service.createCategory(name));

        assertEquals(HttpStatus.FOUND,ex.getStatus());
        assertEquals("Category already exists.",ex.getMessage());
    }
    /**
     * @author Maissa
     * Test for creating a invalid category.
     */
    @Test
    public void testCreateInvalidCategory(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.createCategory(null));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Category name must not be empty.", ex.getMessage());
        verify(repo,never()).save(any(Category.class));
    }
    /**
     * @author Maissa
     * Test for getting a valid category.
     */

    @Test
    public void testGetCategoryByValidId(){
        when(repo.findCategoryByCategoryId(ID)).thenReturn(new Category(name));

        Category foundCategory = service.findCategoryById(ID);

        assertNotNull(foundCategory);
        assertEquals(name,foundCategory.getCategoryName());
    }
    /**
     * @author Maissa
     * Test for getting an invalid category.
     */
    @Test
    public void testGetCategoryByInvalidId(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.findCategoryById(-1));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The Category ID "+ -1 +"is not valid", ex.getMessage());
    }
    /**
     * @author Maissa
     * Test for getting a non existent category.
     */
    @Test
    public void testGetCategoryByNonExistentId(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.findCategoryById(ID));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("There is no Category with ID "+ ID+".", ex.getMessage());
    }
    /**
     * @author Maissa
     * Test for updating a valid category.
     */
    @Test
    public void testUpdateValidCategory(){
        Category c= new Category(name);
        when(repo.findCategoryByCategoryId(ID)).thenReturn(c);
        when(repo.save(any(Category.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        Category updatedCategory = service.updateCategory(ID,"Sports");

        assertNotNull(updatedCategory);
        assertEquals("Sports",updatedCategory.getCategoryName());
        verify(repo,times(1)).save(updatedCategory);
    }
    /**
     * @author Maissa
     * Test for updating a non existent category.
     */
    @Test
    public void testUpdateNonExistentCategory(){
        when(repo.findCategoryByCategoryId(ID)).thenReturn(null);
        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateCategory(ID,"Sports"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("There is no Category with ID"+ ID+".", ex.getMessage());
    }
    /**
     * @author Maissa
     * Test for updating a category to an already existing one.
     */

    @Test
    public void testUpdateDuplicateCategory(){

        Category c = new Category(name);
        Iterable<Category> categories = List.of(c, new Category("Sports"));
        when(repo.findAll()).thenReturn(categories);
        when(repo.findCategoryByCategoryId(ID)).thenReturn(c);

        GameShopException ex = assertThrows(GameShopException.class, () -> service.updateCategory(ID,"Sports"));

        assertEquals(HttpStatus.FOUND, ex.getStatus());
        assertEquals("Category already exists.", ex.getMessage());
    }
    /**
     * @author Maissa
     * Test for deleting a valid category.
     */
    @Test
    public void testDeleteValidCategory(){
        when(repo.findCategoryByCategoryId(ID)).thenReturn(new Category(name));
        service.deleteCategory(ID);

        verify(repo, times(1)).findCategoryByCategoryId(ID);
        verify(repo, times(1)).deleteById(ID);

    }
    /**
     * @author Maissa
     * Test for deleting an invalid category.
     */
    @Test
    public void testDeleteInvalidCategory(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.deleteCategory(-1));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The Category ID "+ -1 +"is not valid", ex.getMessage());
    }
    /**
     * @author Maissa
     * Test for deleting a non-existent category.
     */
    @Test
    public void testDeleteNonExistentCategory(){
        GameShopException ex = assertThrows(GameShopException.class, () -> service.deleteCategory(ID));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("There is no Category with ID "+ ID+".", ex.getMessage());
    }
    /**
     * @author Maissa
     * Test for getting all categories.
     */

    @Test
    public void testGetAllCategories(){
        Iterable<Category> categories = List.of(new Category("Action"), new Category("Comedy"));
        when(repo.findAll()).thenReturn(categories);

        Iterable<Category> foundCategories = service.getAllCategories();

        assertNotNull(foundCategories);
        assertEquals(categories, foundCategories);
        verify(repo, times(1)).findAll();
    }

}
