/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package ca.mcgill.ecse321.gameshop.model;
import java.util.*;


import jakarta.persistence.*;

/**
 * Manager class extending Role
 */
// line 39 "model.ump"
// line 123 "model.ump"
@Entity
public class Manager extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  @OneToMany
  private List<Review> reviews;
  @OneToMany
  private List<Game> games;

  //------------------------
  // CONSTRUCTOR
  //------------------------
public Manager(){

}
  public Manager( Person aPerson)
  {
    super(aPerson);
    reviews = new ArrayList<Review>();
    games = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  /* Code from template association_GetMany */
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(Review.StarRating aRating, String aComment, int aAmountOfLikes,  String aReply, Customer aCustomer, Game aGame)
  {
    return new Review(aRating, aComment, aAmountOfLikes,  aReply, aCustomer, this,aGame);
   // (aRating, aComment, aAmountOfLikes,  aReply, aCustomer, aManager, this)
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Manager existingManager = aReview.getManager();
    boolean isNewManager = existingManager != null && !this.equals(existingManager);
    if (isNewManager)
    {
      aReview.setManager(this);
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
    //Unable to remove aReview, as it must always have a manager
    if (!this.equals(aReview.getManager()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addGame(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL, int aGameId, boolean aToBeAdded, boolean aToBeRemoved, Employee aCreator, Category... allCategories)
  {
    return new Game(aName, aDescription, aPrice, aStockQuantity, aPhotoURL, aToBeAdded, aToBeRemoved, this, aCreator, allCategories);
  }

  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    Manager existingManager = aGame.getManager();
    boolean isNewManager = existingManager != null && !this.equals(existingManager);
    if (isNewManager)
    {
      aGame.setManager(this);
    }
    else
    {
      games.add(aGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    //Unable to remove aGame, as it must always have a manager
    if (!this.equals(aGame.getManager()))
    {
      games.remove(aGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {  
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameAt(aGame, index);
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
    for(int i=games.size(); i > 0; i--)
    {
      Game aGame = games.get(i - 1);
      aGame.delete();
    }
    super.delete();
  }

}