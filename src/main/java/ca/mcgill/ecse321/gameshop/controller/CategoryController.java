package ca.mcgill.ecse321.gameshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.dto.CategoryListDto;
import ca.mcgill.ecse321.gameshop.dto.CategoryRequestDto;
import ca.mcgill.ecse321.gameshop.dto.CategoryResponseDto;
import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.service.CategoryService;

@RestController
//@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto c){

        Category createdCategory = categoryService.createCategory(c.getCategoryName());

        return new CategoryResponseDto(createdCategory);
    }
    @PutMapping("/categories/{ID}")
    public CategoryResponseDto updateCategory(@PathVariable int ID, @RequestBody String name){
        return new CategoryResponseDto(categoryService.updateCategory(ID,name));
    }

    @GetMapping("/categories/{ID}")
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