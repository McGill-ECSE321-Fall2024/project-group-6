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
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto c){
        Category createdCategory = categoryService.createCategory(c.getCategoryName());
        return new CategoryResponseDto(createdCategory);
    }
    @PutMapping("categories/{ID}/{name}")
    public CategoryResponseDto updateCategory(@PathVariable int ID, @PathVariable String name){
        return new CategoryResponseDto(categoryService.updateCategory(ID,name));
    }

    @GetMapping("categories/{ID}")
    public CategoryResponseDto findCategoryById(@PathVariable int ID){
        return new CategoryResponseDto(categoryService.findCategoryById(ID));
    }

    @GetMapping("/categories")
    public CategoryListDto getAllCategories(){
        List<CategoryResponseDto> categories = new ArrayList<>();
        for (Category c: categoryService.getAllCategories()){
            categories.add(new CategoryResponseDto(c));
        }
        return new CategoryListDto(categories);
    }

    @DeleteMapping("/categories/{ID}")
    public void deleteCategory(@PathVariable int ID){
        categoryService.deleteCategory(ID);
    }


}
