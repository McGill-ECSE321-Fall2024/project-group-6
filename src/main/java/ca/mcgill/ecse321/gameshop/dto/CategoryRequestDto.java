package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Game;

import java.util.List;

public class CategoryRequestDto {
    private String name;
    private List<Game> games;

    public String getCategoryName(){
        return name;
    }
    public List<Game> getCategoryGames(){
        return games;
    }
    public void setName(String newName){
        this.name=newName;
    }

}
