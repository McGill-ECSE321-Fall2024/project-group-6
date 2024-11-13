/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package ca.mcgill.ecse321.gameshop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Game class
 */
// line 59 "model.ump"
// line 138 "model.ump"
@Entity
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private String name;
  private String description;
  private float price;
  private int stockQuantity;
  private String photoURL;
  @Id
  @GeneratedValue
  private int gameId;
  private boolean toBeAdded;
  private boolean toBeRemoved;
  private float promotion;

  //Game Associations
  @OneToMany
  private List<Review> reviews;
  @ManyToOne
  private Manager manager;
  @ManyToOne
  private Employee creator;
  @ManyToMany
  private List<Guest> guests;
 @ManyToMany
  private List<Category> categories;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Game(){

  }
  public Game(String aName, String aDescription, float aPrice, int aStockQuantity,String aPhotoURL)
  {
    name = aName;
    description = aDescription;
    price = aPrice;
    photoURL = aPhotoURL;
    stockQuantity = aStockQuantity;
    reviews = new ArrayList<Review>();

    guests = new ArrayList<Guest>();
    categories = new ArrayList<Category>();

  }

  public Game(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL,  boolean aToBeAdded, boolean aToBeRemoved, Manager aManager, Employee aCreator, List<Category> allCategories)
  {
    name = aName;
    description = aDescription;
    price = aPrice;
    stockQuantity = aStockQuantity;
    photoURL = aPhotoURL;
    toBeAdded = aToBeAdded;
    toBeRemoved = aToBeRemoved;
    reviews = new ArrayList<Review>();
    boolean didAddManager = setManager(aManager);
    if (!didAddManager)
    {
      throw new RuntimeException("Unable to create game due to manager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCreator = setCreator(aCreator);
    if (!didAddCreator)
    {
      throw new RuntimeException("Unable to create created due to creator. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    guests = new ArrayList<Guest>();
    categories = new ArrayList<Category>();
    boolean didAddCategories = setCategories(allCategories);
    if (!didAddCategories)
    {
      throw new RuntimeException("Unable to create Game, must have at least 1 categories. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  //(aName,aDescription,aPrice,aStockQuantity,aPhotoURL,tobeAdded,allCategories)
  public Game(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL, List<Category> allCategories)
  {
    name = aName;
    description = aDescription;
    price = aPrice;
    stockQuantity = aStockQuantity;
    photoURL = aPhotoURL;
    toBeAdded = true;

    reviews = new ArrayList<Review>();


    guests = new ArrayList<Guest>();
    categories = new ArrayList<Category>();
    boolean didAddCategories = setCategories(allCategories);
    if (!didAddCategories)
    {
      throw new RuntimeException("Unable to create Game, must have at least 1 categories. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(float aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setStockQuantity(int aStockQuantity)
  {
    boolean wasSet = false;
    stockQuantity = aStockQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhotoURL(String aPhotoURL)
  {
    boolean wasSet = false;
    photoURL = aPhotoURL;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameId(int aGameId)
  {
    boolean wasSet = false;
    gameId = aGameId;
    wasSet = true;
    return wasSet;
  }

  public boolean setToBeAdded(boolean aToBeAdded)
  {
    boolean wasSet = false;
    toBeAdded = aToBeAdded;
    wasSet = true;
    return wasSet;
  }

  public boolean setToBeRemoved(boolean aToBeRemoved)
  {
    boolean wasSet = false;
    toBeRemoved = aToBeRemoved;
    wasSet = true;
    return wasSet;
  }

  public boolean setPromotion(float aPromotion)
  {
    boolean wasSet = false;
    promotion = aPromotion;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public float getPrice()
  {
    return price;
  }

  public int getStockQuantity()
  {
    return stockQuantity;
  }

  public String getPhotoURL()
  {
    return photoURL;
  }

  public int getGameId()
  {
    return gameId;
  }

  public boolean getToBeAdded()
  {
    return toBeAdded;
  }

  public boolean getToBeRemoved()
  {
    return toBeRemoved;
  }

  public float getPromotion()
  {
    return promotion;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isToBeAdded()
  {
    return toBeAdded;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isToBeRemoved()
  {
    return toBeRemoved;
  }
  /* Code from template association_GetMany */
  public Review getReview(int index)
  {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }
  /* Code from template association_GetOne */
  public Employee getCreator()
  {
    return creator;
  }
  /* Code from template association_GetMany */
  public Guest getGuest(int index)
  {
    Guest aGuest = guests.get(index);
    return aGuest;
  }

  public List<Guest> getGuests()
  {
    List<Guest> newGuests = Collections.unmodifiableList(guests);
    return newGuests;
  }

  public int numberOfGuests()
  {
    int number = guests.size();
    return number;
  }

  public boolean hasGuests()
  {
    boolean has = guests.size() > 0;
    return has;
  }

  public int indexOfGuest(Guest aGuest)
  {
    int index = guests.indexOf(aGuest);
    return index;
  }
  /* Code from template association_GetMany */
  public Category getCategory(int index)
  {
    Category aCategory = categories.get(index);
    return aCategory;
  }

  public List<Category> getCategories()
  {
    //List<Category> newCategories = Collections.unmodifiableList(categories);
    return this.categories;
  }

  public int numberOfCategories()
  {
    int number = categories.size();
    return number;
  }

  public boolean hasCategories()
  {
    boolean has = categories.size() > 0;
    return has;
  }

  public int indexOfCategory(Category aCategory)
  {
    int index = categories.indexOf(aCategory);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(Review.StarRating aRating, String aComment, int aAmountOfLikes,  String aReply, Customer aCustomer, Manager aManager)
  {
    return new Review(aRating, aComment, aAmountOfLikes,  aReply, aCustomer, aManager, this);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Game existingGame = aReview.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aReview.setGame(this);
    }
    else
    {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    //Unable to remove aReview, as it must always have a game
    if (!this.equals(aReview.getGame()))
    {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index)
  {
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    else
    {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setManager(Manager aManager)
  {
    boolean wasSet = false;
    if (aManager == null)
    {
      return wasSet;
    }

    Manager existingManager = manager;
    manager = aManager;
    if (existingManager != null && !existingManager.equals(aManager))
    {
      existingManager.removeGame(this);
    }
    manager.addGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCreator(Employee aCreator)
  {
    boolean wasSet = false;
    if (aCreator == null)
    {
      return wasSet;
    }

    Employee existingCreator = creator;
    creator = aCreator;
    if (existingCreator != null && !existingCreator.equals(aCreator))
    {
      existingCreator.removeCreated(this);
    }
    creator.addCreated(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGuests()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addGuest(Guest aGuest)
  {
    boolean wasAdded = false;
    if (guests.contains(aGuest)) { return false; }
    guests.add(aGuest);
    if (aGuest.indexOfGame(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aGuest.addGame(this);
      if (!wasAdded)
      {
        guests.remove(aGuest);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeGuest(Guest aGuest)
  {
    boolean wasRemoved = false;
    if (!guests.contains(aGuest))
    {
      return wasRemoved;
    }

    int oldIndex = guests.indexOf(aGuest);
    guests.remove(oldIndex);
    if (aGuest.indexOfGame(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aGuest.removeGame(this);
      if (!wasRemoved)
      {
        guests.add(oldIndex,aGuest);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGuestAt(Guest aGuest, int index)
  {
    boolean wasAdded = false;
    if(addGuest(aGuest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuests()) { index = numberOfGuests() - 1; }
      guests.remove(aGuest);
      guests.add(index, aGuest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGuestAt(Guest aGuest, int index)
  {
    boolean wasAdded = false;
    if(guests.contains(aGuest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuests()) { index = numberOfGuests() - 1; }
      guests.remove(aGuest);
      guests.add(index, aGuest);
      wasAdded = true;
    }
    else
    {
      wasAdded = addGuestAt(aGuest, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfCategoriesValid()
  {
    boolean isValid = numberOfCategories() >= minimumNumberOfCategories();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCategories()
  {
    return 1;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCategory(Category aCategory)
  {
    boolean wasAdded = false;
    if (categories.contains(aCategory)) { return false; }
    categories.add(aCategory);
    if (aCategory.indexOfGame(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCategory.addGame(this);
      if (!wasAdded)
      {
        categories.remove(aCategory);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeCategory(Category aCategory)
  {
    boolean wasRemoved = false;
    if (!categories.contains(aCategory))
    {
      return wasRemoved;
    }

    if (numberOfCategories() <= minimumNumberOfCategories())
    {
      return wasRemoved;
    }

    int oldIndex = categories.indexOf(aCategory);
    categories.remove(oldIndex);
    if (aCategory.indexOfGame(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCategory.removeGame(this);
      if (!wasRemoved)
      {
        categories.add(oldIndex,aCategory);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  public boolean setCategories(List<Category> newCategories)
  {

    boolean wasSet = false;
    ArrayList<Category> verifiedCategories = new ArrayList<Category>();
    for (Category aCategory : newCategories)
    {
      if (verifiedCategories.contains(aCategory))
      {
        continue;
      }
      verifiedCategories.add(aCategory);
    }

    if (verifiedCategories.size() != newCategories.size() || verifiedCategories.size() < minimumNumberOfCategories())
    {
      return wasSet;
    }

    ArrayList<Category> oldCategories = new ArrayList<Category>(categories);
    categories.clear();
    for (Category aNewCategory : verifiedCategories)
    {
      categories.add(aNewCategory);
      if (oldCategories.contains(aNewCategory))
      {
        oldCategories.remove(aNewCategory);
      }
      else
      {
        aNewCategory.addGame(this);
      }
    }

    for (Category anOldCategory : oldCategories)
    {
      anOldCategory.removeGame(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCategoryAt(Category aCategory, int index)
  {
    boolean wasAdded = false;
    if(addCategory(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCategoryAt(Category aCategory, int index)
  {
    boolean wasAdded = false;
    if(categories.contains(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    }
    else
    {
      wasAdded = addCategoryAt(aCategory, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=reviews.size(); i > 0; i--)
    {
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    Manager placeholderManager = manager;
    this.manager = null;
    if(placeholderManager != null)
    {
      placeholderManager.removeGame(this);
    }
    Employee placeholderCreator = creator;
    this.creator = null;
    if(placeholderCreator != null)
    {
      placeholderCreator.removeCreated(this);
    }
    ArrayList<Guest> copyOfGuests = new ArrayList<Guest>(guests);
    guests.clear();
    for(Guest aGuest : copyOfGuests)
    {
      aGuest.removeGame(this);
    }
    ArrayList<Category> copyOfCategories = new ArrayList<Category>(categories);
    categories.clear();
    for(Category aCategory : copyOfCategories)
    {
      aCategory.removeGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "price" + ":" + getPrice()+ "," +
            "stockQuantity" + ":" + getStockQuantity()+ "," +
            "photoURL" + ":" + getPhotoURL()+ "," +
            "gameId" + ":" + getGameId()+ "," +
            "toBeAdded" + ":" + getToBeAdded()+ "," +
            "toBeRemoved" + ":" + getToBeRemoved()+ "," +
            "promotion" + ":" + getPromotion()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "creator = "+(getCreator()!=null?Integer.toHexString(System.identityHashCode(getCreator())):"null");
  }
}