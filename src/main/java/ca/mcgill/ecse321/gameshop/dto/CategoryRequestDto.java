package ca.mcgill.ecse321.gameshop.dto;

public class CategoryRequestDto {

    public String name;


    public CategoryRequestDto() {}

    public CategoryRequestDto(String title){
        this.name=title;
    }

    public String getCategoryName(){
        return this.name;
    }

    public void setName(String newName){
        this.name=newName;
    }

}