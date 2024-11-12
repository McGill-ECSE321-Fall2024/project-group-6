package ca.mcgill.ecse321.gameshop.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {
    @NotBlank(message = "Category name is required.")
    private String name;


    public CategoryRequestDto() {}

    public CategoryRequestDto(String title){
        this.name=title;
    }

    public String getCategoryName(){
        return name;
    }

    public void setName(String newName){
        this.name=newName;
    }

}
