package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class CategoryListDto {
    private List<CategoryResponseDto> categories;

    public CategoryListDto(List<CategoryResponseDto> categories){
        this.categories=categories;
    }

    public List<CategoryResponseDto> getCategories(){
        return categories;
    }

    public void setCategories(List<CategoryResponseDto> categories){
        this.categories=categories;
    }
}
