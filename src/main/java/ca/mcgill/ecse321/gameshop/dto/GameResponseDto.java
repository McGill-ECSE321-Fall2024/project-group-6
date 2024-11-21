package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;

import java.util.List;


public class GameResponseDto {

    private String name;
    private String description;
    private float price;
    private int stockQuantity;

    private String photoURL;
    private int gameId;
    private boolean toBeAdded;

    private boolean toBeRemoved;
    private float promotion;

    private List<Category> categories;

    public GameResponseDto(){};
    public GameResponseDto(Game game) {
        this.gameId=game.getGameId();
        this.description = game.getDescription();
        this.name = game.getName();
        this.photoURL = game.getPhotoURL();
        this.price = game.getPrice();
        this.promotion = game.getPromotion();
        this.toBeAdded = game.getToBeAdded();
        this.toBeRemoved = game.getToBeRemoved();
        this.stockQuantity = game.getStockQuantity();
        this.categories=game.getCategories();
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

    public boolean setGameId(int aGameId) {
        boolean wasSet = false;
        gameId = aGameId;
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

    public int getGameId() {
        return gameId;
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
        return categories;
    }
}