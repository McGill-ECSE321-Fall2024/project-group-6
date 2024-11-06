package ca.mcgill.ecse321.gameshop.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ca.mcgill.ecse321.gameshop.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRequestDto {


    @NotBlank(message = "Name needs to exist")
    private String name;
    @NotBlank(message = "Description needs to exist")
    private String description;
    @NotBlank(message = "price needs to exist")
    private float price;
    @NotBlank(message = "Stock quantity needs to exist")
    private int stockQuantity;
    @NotBlank(message = "Photo url needs to exist")
    private String photoURL;
    @NotBlank(message = "id needs to exist")
    private int gameId;

    private boolean toBeAdded;
    private boolean toBeRemoved;
    private float promotion;


    private List<Review> reviews;
    private Manager manager;
    private Employee creator;
    private List<Guest> guests;
    private List<Category> categories;


    public GameRequestDto(String aName, String aDescription, float aPrice, int aStockQuantity,
                           String aPhotoURL, boolean aToBeAdded, boolean aToBeRemoved, float aPromotion, Manager aManager,
                           Employee aCreator, List<Guest> aGuests, Category... allCategories) {
        this.name = aName;
        this.description = aDescription;
        this.price = aPrice;
        this.stockQuantity = aStockQuantity;
        this.photoURL = aPhotoURL;
        this.toBeAdded = aToBeAdded;
        this.toBeRemoved = aToBeRemoved;
        this.manager = aManager;
        this.promotion = aPromotion;
        this.creator = aCreator;
        this.categories = List.of(allCategories);
        this.guests = aGuests;
        //this.assignedTasks=tasks;

    }

    public GameRequestDto(String aName, String aDescription, float aPrice, int aStockQuantity,
                           String aPhotoURL, boolean aToBeAdded, boolean aToBeRemoved, float aPromotion) {
        this.name = aName;
        this.description = aDescription;
        this.price = aPrice;
        this.stockQuantity = aStockQuantity;
        this.photoURL = aPhotoURL;
        this.toBeAdded = aToBeAdded;
        this.toBeRemoved = aToBeRemoved;
        this.promotion = aPromotion;

        //this.assignedTasks=tasks;

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

    /* Code from template attribute_IsBoolean */
    public boolean isToBeAdded() {
        return toBeAdded;
    }

    /* Code from template attribute_IsBoolean */
    public boolean isToBeRemoved() {
        return toBeRemoved;
    }

    /* Code from template association_GetMany */
    public Review getReview(int index) {
        Review aReview = reviews.get(index);
        return aReview;
    }

    public List<Review> getReviews() {
        List<Review> newReviews = Collections.unmodifiableList(reviews);
        return newReviews;
    }

    public int numberOfReviews() {
        int number = reviews.size();
        return number;
    }

    public boolean hasReviews() {
        boolean has = reviews.size() > 0;
        return has;
    }

    public int indexOfReview(Review aReview) {
        int index = reviews.indexOf(aReview);
        return index;
    }

    /* Code from template association_GetOne */
    public Manager getManager() {
        return manager;
    }

    /* Code from template association_GetOne */
    public Employee getCreator() {
        return creator;
    }

    /* Code from template association_GetMany */
    public Guest getGuest(int index) {
        Guest aGuest = guests.get(index);
        return aGuest;
    }

    public List<Guest> getGuests() {
        List<Guest> newGuests = Collections.unmodifiableList(guests);
        return newGuests;
    }

    public int numberOfGuests() {
        int number = guests.size();
        return number;
    }

    public boolean hasGuests() {
        boolean has = guests.size() > 0;
        return has;
    }

    public int indexOfGuest(Guest aGuest) {
        int index = guests.indexOf(aGuest);
        return index;
    }

    /* Code from template association_GetMany */
    public Category getCategory(int index) {
        Category aCategory = categories.get(index);
        return aCategory;
    }

    public List<Category> getCategories() {
        List<Category> newCategories = Collections.unmodifiableList(categories);
        return newCategories;
    }

    public int numberOfCategories() {
        int number = categories.size();
        return number;
    }

    public boolean hasCategories() {
        boolean has = categories.size() > 0;
        return has;
    }

    public int indexOfCategory(Category aCategory) {
        int index = categories.indexOf(aCategory);
        return index;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfReviews() {
        return 0;
    }

    /* Code from template association_IsNumberOfValidMethod */
    public boolean isNumberOfCategoriesValid() {
        boolean isValid = numberOfCategories() >= minimumNumberOfCategories();
        return isValid;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfCategories() {
        return 1;
    }




}