package ca.mcgill.ecse321.gameshop.dto;

import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Category;
import jakarta.validation.constraints.NotBlank;

public class GameRequestDto {

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

    private boolean toBeAdded;

    private boolean toBeRemoved;
    private float promotion;
    private List<Category> categories;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GameRequestDto() {
    }

    public GameRequestDto(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL,  boolean aToBeAdded, boolean aToBeRemoved, float aPromotion, List<Category> allCategories) {
        this.description = aDescription;
        this.name = aName;
        this.photoURL = aPhotoURL;
        this.price = aPrice;
        this.promotion = aPromotion;
        this.toBeAdded = aToBeAdded;
        this.toBeRemoved = aToBeRemoved;
        this.stockQuantity =aStockQuantity;
        this.categories= allCategories;
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

    public boolean getToBeAdded() {

        return toBeAdded;
    }

    public boolean getToBeRemoved() {

        return toBeRemoved;
    }

    public float getPromotion() {

        return promotion;
    }
    
    public List<Category> getCategories()
    {
        List<Category> newCategories = Collections.unmodifiableList(categories);
        return newCategories;
    }
}