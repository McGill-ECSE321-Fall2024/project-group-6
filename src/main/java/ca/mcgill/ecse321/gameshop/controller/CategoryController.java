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
     *
     * @param c
     * @return
     */
    @PostMapping("/categories")
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto c){
        Category createdCategory = categoryService.createCategory(c.getCategoryName());
        return  new CategoryResponseDto(createdCategory);
    }

    /**
     *
     * @param cId
     * @param name
     * @return
     */
    @PutMapping("/categories/{cId}/{name}")
    public CategoryResponseDto updateCategory(@PathVariable int cId, @PathVariable String name){
        return new CategoryResponseDto(categoryService.updateCategory(cId,name));
    }

    /**
     *
     * @param cId
     * @return
     */
    @GetMapping("/categories/{cId}")
    public CategoryResponseDto findCategoryById(@PathVariable int cId){
        return new CategoryResponseDto(categoryService.findCategoryById(cId));
    }

    /**
     *
     * @param cId
     * @return
     */
    @GetMapping("/categories")
    public CategoryListDto getAllCategories(@PathVariable int cId){
        List<CategoryResponseDto> categories = new ArrayList<>();
        for (Category c: categoryService.getAllCategories()){
            categories.add(new CategoryResponseDto(c));
        }
        return new CategoryListDto(categories);
    }

    /**
     *
     * @param cId
     */
    @DeleteMapping("/categories")
    public void deleteCategory(@PathVariable int cId){
        categoryService.deleteCategory(cId);
    }


}