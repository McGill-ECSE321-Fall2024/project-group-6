package ca.mcgill.ecse321.gameshop.controller;

import ca.mcgill.ecse321.gameshop.dto.CategoryRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CategoryResponseDto;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto c){
        Category createdCategory = categoryService.createCategory(c.getCategoryName());
        return  new CategoryResponseDto(createdCategory);
    }

    @GetMapping("/categories/{cId}")
    public CategoryResponseDto findCategoryById(@PathVariable int cId){
        return new CategoryResponseDto(categoryService.findCategoryById(cId));
    }
    /*
    @GetMapping("/categories/{cId}")
    public CategoryResponseDto getCategoryGames(@PathVariable int cId){
        return new CategoryResponseDto(categoryService.getAllCategoryGames(cId));
    }
    */

}
