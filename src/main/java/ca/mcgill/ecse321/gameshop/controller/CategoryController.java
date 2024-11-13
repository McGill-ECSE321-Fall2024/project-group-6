package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.CategoryListDto;
import ca.mcgill.ecse321.gameshop.dto.CategoryRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CategoryResponseDto;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @author Maissa
     * Creates a new category.
     *
     * @param  c
     * @return DTO response
     */
    @PostMapping("/categories")
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto c) {
        Category createdCategory = categoryService.createCategory(c.getCategoryName());
        return new CategoryResponseDto(createdCategory);
    }

    /**
     * @author Maissa
     * Updates an existing category's name by its ID.
     *
     * @param ID
     * @param name
     * @return A DTO response containing the updated category's details.
     */
    @PutMapping("/categories/{ID}")
    public CategoryResponseDto updateCategory(@PathVariable int ID, @RequestBody String name) {
        return new CategoryResponseDto(categoryService.updateCategory(ID, name));
    }

    /**
     * @author Maissa
     * Retrieves a category by its ID.
     *
     * @param ID
     * @return A DTO response
     */
    @GetMapping("/categories/{ID}")
    public CategoryResponseDto findCategoryById(@PathVariable int ID) {
        return new CategoryResponseDto(categoryService.findCategoryById(ID));
    }

    /**
     * @author Maissa
     * Retrieves all categories.
     *
     * @return A DTO list of all categories.
     */
    @GetMapping("/categories")
    public CategoryListDto getAllCategories() {
        List<CategoryResponseDto> categories = new ArrayList<>();
        for (Category c : categoryService.getAllCategories()) {
            categories.add(new CategoryResponseDto(c));
        }
        return new CategoryListDto(categories);
    }

    /**
     * @author Maissa
     * Deletes a category by its ID.
     *
     * @param ID
     */
    @DeleteMapping("/categories/{ID}")
    public void deleteCategory(@PathVariable int ID) {
        categoryService.deleteCategory(ID);
    }
}
