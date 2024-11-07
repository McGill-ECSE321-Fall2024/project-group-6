package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.*;

import java.util.Collections;
import java.util.List;

public class GameRequestDTO {
    private String name;
    private String description;
    private float price;
    private int stockQuantity;
    private String photoURL;
    private boolean toBeAdded;
    private boolean toBeRemoved;
    private float promotion;
    private List<Category> categories;

    public GameRequestDTO(String name, String description, float price, int stockQuantity, String photoURL,  boolean toBeAdded, boolean toBeRemoved, float promotion,Category... allCategories) {
        this.description = description;
        this.name = name;
        this.photoURL = photoURL;
        this.price = price;
        this.promotion = promotion;
        this.toBeAdded = toBeAdded;
        this.toBeRemoved = toBeRemoved;
        this.stockQuantity =stockQuantity;
        this.categories= List.of(allCategories);
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
}
