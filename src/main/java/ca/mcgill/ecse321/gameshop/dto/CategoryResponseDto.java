package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Category;

public class CategoryResponseDto {
    private int id;
    private String name;

    @SuppressWarnings("unused")
    private CategoryResponseDto() {
    }

    public CategoryResponseDto(Category c){
        this.id=c.getCategoryId();
        this.name=c.getCategoryName();
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
}