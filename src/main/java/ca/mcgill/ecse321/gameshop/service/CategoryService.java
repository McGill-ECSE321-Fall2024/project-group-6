package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    /**
     * @author Maissa
     * Creates a new category with the specified name.
     *
     * @param name
     * @return Created category.
     * @throws GameShopException
     */
    @Transactional
    public Category createCategory(String name) {
        if (name == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "Category name must not be empty.");
        }

        for (Category i : repo.findAll()) {
            if (i.getCategoryName().equals(name)) {
                throw new GameShopException(HttpStatus.FOUND, "Category already exists.");
            }
        }

        Category c = new Category(name);
        return repo.save(c);
    }

    /**
     * @author Maissa
     * Finds a category by its ID.
     *
     * @param cId
     * @return The category with the specified ID.
     * @throws GameShopException
     */
    public Category findCategoryById(int cId) {
        if (cId < 0) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "The Category ID " + cId + " is not valid.");
        } else if (repo.findCategoryByCategoryId(cId) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "There is no Category with ID " + cId + ".");
        }

        return repo.findCategoryByCategoryId(cId);
    }

    /**
     * @author Maissa
     * Updates the name of an existing category by ID.
     *
     * @param cId
     * @param name
     * @return The updated category.
     * @throws GameShopException i
     */
    @Transactional
    public Category updateCategory(int cId, String name) {
        if (cId < 0) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "The Category ID " + cId + " is not valid.");
        } else if (repo.findCategoryByCategoryId(cId) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "There is no Category with ID " + cId + ".");
        }

        for (Category i : repo.findAll()) {
            if (i.getCategoryName().equals(name)) {
                throw new GameShopException(HttpStatus.FOUND, "Category already exists.");
            }
        }

        Category toUpdate = repo.findCategoryByCategoryId(cId);
        toUpdate.setCategoryName(name);
        return repo.save(toUpdate);
    }

    /**
     * @author Maissa
     * Deletes a category by ID.
     *
     * @param cId
     * @throws GameShopException
     */
    public void deleteCategory(int cId) {
        if (cId < 0) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "The Category ID " + cId + " is not valid.");
        } else if (repo.findCategoryByCategoryId(cId) == null) {
            throw new GameShopException(HttpStatus.NOT_FOUND, "There is no Category with ID " + cId + ".");
        }

        repo.deleteById(cId);
    }

    /**
     * @author Maissa
     * Retrieves all categories from the repository.
     *
     * @return All categories.
     */
    public Iterable<Category> getAllCategories() {
        return repo.findAll();
    }
}
