package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;

import java.util.List;

public class CategoryResponseDto {
    private int id;
    private String name;
    private List<Game> games;

    public CategoryResponseDto(Category c){
        this.id=c.getCategoryId();
        this.name=c.getCategoryName();
        this.games=c.getGames();
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public List<Game> getCategoryGames(){
        return games;
    }
    public void setName(String newName){
        this.name=newName;
    }


}
