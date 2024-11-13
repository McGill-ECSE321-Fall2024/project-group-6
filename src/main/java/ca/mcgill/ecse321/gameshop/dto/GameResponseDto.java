package ca.mcgill.ecse321.gameshop.dto;

import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.gameshop.model.Category;
import ca.mcgill.ecse321.gameshop.model.Game;


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

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GameResponseDto() {
    }

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
        this.categories = game.getCategories() != null ? game.getCategories() : Collections.emptyList();
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

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }
}