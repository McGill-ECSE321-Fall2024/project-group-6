package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Category;

public class CategoryResponseDto {
    private int id;
    private String name;

    public CategoryResponseDto() {
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


    public void setName(String newName){
        this.name=newName;
    }


}
