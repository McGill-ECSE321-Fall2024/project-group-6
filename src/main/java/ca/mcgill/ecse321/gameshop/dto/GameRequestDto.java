package ca.mcgill.ecse321.gameshop.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class GameRequestDto {

    @NotBlank(message = "Name of the game can not be empty")
    public String name;
    @NotBlank(message = "Description of the game can not be empty")
    public String description;
    @NotBlank(message = "Price of the game can not be empty")
    public float price;
    @NotBlank(message = "Stock Quantity of the game can not be empty")
    public int stockQuantity;
    @NotBlank(message = "PhotoUrl of the game can not be empty")
    public String photoURL;

    public boolean toBeAdded;

    public boolean toBeRemoved;
    public float promotion;
    public List<Integer> categories;
    public GameRequestDto(){};
    public GameRequestDto(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL,  boolean aToBeAdded, boolean aToBeRemoved, float aPromotion,List<Integer> allCategories) {
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

    public boolean setName(String aName) {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setDescription(String aDescription) {
        boolean wasSet = false;
        description = aDescription;
        wasSet = true;
        return wasSet;
    }

    public boolean setPrice(float aPrice) {
        boolean wasSet = false;
        price = aPrice;
        wasSet = true;
        return wasSet;
    }

    public boolean setStockQuantity(int aStockQuantity) {
        boolean wasSet = false;
        stockQuantity = aStockQuantity;
        wasSet = true;
        return wasSet;
    }

    public boolean setPhotoURL(String aPhotoURL) {
        boolean wasSet = false;
        photoURL = aPhotoURL;
        wasSet = true;
        return wasSet;
    }


    public boolean setToBeAdded(boolean aToBeAdded) {
        boolean wasSet = false;
        toBeAdded = aToBeAdded;
        wasSet = true;
        return wasSet;
    }

    public boolean setToBeRemoved(boolean aToBeRemoved) {
        boolean wasSet = false;
        toBeRemoved = aToBeRemoved;
        wasSet = true;
        return wasSet;
    }

    public boolean setPromotion(float aPromotion) {
        boolean wasSet = false;
        promotion = aPromotion;
        wasSet = true;
        return wasSet;
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
    public List<Integer> getCategories() {
        return categories;
    }
}