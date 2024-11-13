package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.*;
import jakarta.validation.constraints.NotBlank;

/**
 * This method is here to help testing customer addition of a game.
 */

public class GameRequestDto2 {

    @NotBlank(message = "Name of the game can not be empty")
    private String name;
    @NotBlank(message = "Description of the game can not be empty")
    private String description;
    @NotBlank(message = "Price of the game can not be empty")
    private float price;
    @NotBlank(message = "Stock Quantity of the game can not be empty")
    private int stockQuantity;
    @NotBlank(message = "PhotoUrl of the game can not be empty")
    private String photoURL;


    public GameRequestDto2(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL) {
        this.description = aDescription;
        this.name = aName;
        this.photoURL = aPhotoURL;
        this.price = aPrice;
        this.stockQuantity =aStockQuantity;
    }



    public String getName() {

        return name;
    }

    public String getDescription() {

        return description;
    }

    public float getPrice() {

        return price;
    }

    public int getStockQuantity() {

        return stockQuantity;
    }

    public String getPhotoURL() {

        return photoURL;
    }


}
