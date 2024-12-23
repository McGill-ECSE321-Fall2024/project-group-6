/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321.gameshop.model;
import java.util.*;


import jakarta.persistence.*;

/**
 * Review class
 */
// line 82 "model.ump"


@Entity
public class Review
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum StarRating { OneStar, TwoStar, ThreeStar, FourStar, FiveStar }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  private StarRating rating;
  private String comment;
  private int amountOfLikes;
  @Id
  @GeneratedValue
  private int reviewId;
  private String reply;

  //Review Associations
  @ManyToOne
  private Customer customer;
  @ManyToOne
  private Manager manager;
  @ManyToOne
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------
public Review(){

}
  public Review(StarRating aRating, String aComment, int aAmountOfLikes)
  {
    rating = aRating;
    comment = aComment;
    amountOfLikes = aAmountOfLikes;


  }
  public Review(StarRating aRating, String aComment, int aAmountOfLikes,  String aReply, Customer aCustomer, Manager aManager, Game aGame)
  {
    rating = aRating;
    comment = aComment;
    amountOfLikes = aAmountOfLikes;

    reply = aReply;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create review due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddManager = setManager(aManager);
    if (!didAddManager)
    {
      throw new RuntimeException("Unable to create review due to manager. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create review due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public Review(StarRating aRating, String aComment, int aAmountOfLikes, Customer aCustomer, Game aGame)
  {
    rating = aRating;
    comment = aComment;
    amountOfLikes = aAmountOfLikes;

    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create review due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create review due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRating(StarRating aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public boolean setComment(String aComment)
  {
    boolean wasSet = false;
    comment = aComment;
    wasSet = true;
    return wasSet;
  }

  public boolean setAmountOfLikes(int aAmountOfLikes)
  {
    boolean wasSet = false;
    amountOfLikes = aAmountOfLikes;
    wasSet = true;
    return wasSet;
  }

  public boolean setReviewId(int aReviewId)
  {
    boolean wasSet = false;
    reviewId = aReviewId;
    wasSet = true;
    return wasSet;
  }

  public boolean setReply(String aReply)
  {
    boolean wasSet = false;
    reply = aReply;
    wasSet = true;
    return wasSet;
  }

  public StarRating getRating()
  {
    return rating;
  }

  public String getComment()
  {
    return comment;
  }

  public int getAmountOfLikes()
  {
    return amountOfLikes;
  }

  public int getReviewId()
  {
    return reviewId;
  }

  public String getReply()
  {
    return reply;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeReview(this);
    }
   // customer.addReview(this);
    wasSet = true;
    return wasSet;
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
      existingManager.removeReview(this);
    }
    manager.addReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeReview(this);
    }
    //game.addReview(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeReview(this);
    }
    Manager placeholderManager = manager;
    this.manager = null;
    if(placeholderManager != null)
    {
      placeholderManager.removeReview(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeReview(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "comment" + ":" + getComment()+ "," +
            "amountOfLikes" + ":" + getAmountOfLikes()+ "," +
            "reviewId" + ":" + getReviewId()+ "," +
            "reply" + ":" + getReply()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}