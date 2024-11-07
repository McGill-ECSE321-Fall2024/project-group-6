package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.*;

import java.util.Collections;
import java.util.List;

public class GameResponseDTO {
    private String name;
    private String description;
    private float price;
    private int stockQuantity;
    private String photoURL;
    private boolean toBeAdded;
    private boolean toBeRemoved;
    private float promotion;
    private List<Category> categories;
    private int gameId;

    public GameResponseDTO(Game game) {
        this.description = game.getDescription();
        this.name = game.getName();
        this.photoURL = game.getPhotoURL();
        this.price = game.getPrice();
        this.promotion = game.getPromotion();
        this.toBeAdded = game.getToBeAdded();
        this.toBeRemoved = game.getToBeRemoved();
        this.stockQuantity = game.getStockQuantity();
        this.categories = game.getCategories();
        this.gameId = game.getGameId();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public boolean getToBeAdded() {
        return toBeAdded;
    }
    public void setToBeAdded(boolean toBeAdded) {
        this.toBeAdded = toBeAdded;
    }

    public boolean getToBeRemoved() {
        return toBeRemoved;
    }

    public void setToBeRemoved(boolean toBeRemoved) {
        this.toBeRemoved = toBeRemoved;
    }

    public float getPromotion() {
        return promotion;
    }
    public void setPromotion(float promotion) {
        this.promotion = promotion;
    }
    public List<Category> getCategories() {
        List<Category> newCategories = Collections.unmodifiableList(categories);
        return newCategories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int id) {
        this.gameId = id;
    }
}
