package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;

public class CategoryListDto {
    private List<CategoryResponseDto> categories;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private CategoryListDto() {
    }

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
