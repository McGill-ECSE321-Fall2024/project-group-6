package ca.mcgill.ecse321.gameshop.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

import java.util.*;


import jakarta.persistence.*;


/**
 * Customer class extending Role
 */
// line 19 "model.ump"
// line 113 "model.ump"
@Entity
public class Customer extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String shippingAddress;
  @ElementCollection
  private List<Game> wishlist;
  @ElementCollection
  private List<Game> cart;

  //Customer Associations
  @OneToMany
  private List<Review> reviews;
  @OneToMany
  private List<Payment> payments;
  @OneToMany
  private List<Command> commands;
  //------------------------
  // CONSTRUCTOR
  //------------------------
public Customer(){

}
  public Customer( Person aPerson, String aShippingAddress)
  {
    super(aPerson);
    shippingAddress = aShippingAddress;
    reviews = new ArrayList<Review>();
    payments = new ArrayList<Payment>();
    commands = new ArrayList<Command>();
  }
  public Customer( Person aPerson, String aShippingAddress, List<Game> aWishlist, List<Game> aCart)
  {
    super(aPerson);
    shippingAddress = aShippingAddress;
    wishlist = aWishlist;
    cart = aCart;
    reviews = new ArrayList<Review>();
    payments = new ArrayList<Payment>();
    commands = new ArrayList<Command>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setShippingAddress(String aShippingAddress)
  {
    boolean wasSet = false;
    shippingAddress = aShippingAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setWishlist(List<Game> aWishlist)
  {
    boolean wasSet = false;
    wishlist = aWishlist;
    wasSet = true;
    return wasSet;
  }

  public boolean setCart(List<Game> aCart)
  {
    boolean wasSet = false;
    cart = aCart;
    wasSet = true;
    return wasSet;
  }

  public String getShippingAddress()
  {
    return shippingAddress;
  }

  public List<Game> getWishlist()
  {
    return wishlist;
  }

  public List<Game> getCart()
  {
    return cart;
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
  /* Code from template association_GetMany */
  public Payment getPayment(int index)
  {
    Payment aPayment = payments.get(index);
    return aPayment;
  }

  public List<Payment> getPayments()
  {
    List<Payment> newPayments = Collections.unmodifiableList(payments);
    return newPayments;
  }

  public int numberOfPayments()
  {
    int number = payments.size();
    return number;
  }

  public boolean hasPayments()
  {
    boolean has = payments.size() > 0;
    return has;
  }

  public int indexOfPayment(Payment aPayment)
  {
    int index = payments.indexOf(aPayment);
    return index;
  }

  public Command getCommand(int index)
  {
    Command aCommand = commands.get(index);
    return aCommand;
  }
  public List<Command> getCommands()
  {
    List<Command> newCommands = Collections.unmodifiableList(commands);
    return newCommands;
  }
  public int numberOfCommands()
  {
    int number = commands.size();
    return number;
  }
  public boolean hasCommands()
  {
    boolean has = commands.size() > 0;
    return has;
  }
  public int indexOfCommand(Command aCommand)
  {
    int index = commands.indexOf(aCommand);
    return index;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(Review.StarRating aRating, String aComment, int aAmountOfLikes, int aReviewId, String aReply, Manager aManager, Game aGame)
  {
    return new Review(aRating, aComment, aAmountOfLikes,  aReply, this, aManager, aGame);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Customer existingCustomer = aReview.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aReview.setCustomer(this);
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
    //Unable to remove aReview, as it must always have a customer
    if (!this.equals(aReview.getCustomer()))
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
  public static int minimumNumberOfPayments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Payment addPayment(String aBillingAddress, int aCreditCardNb, String aExpirationDate, int aCvc, int aPaymentId, Command aCommand)
  {
    return new Payment(aBillingAddress, aCreditCardNb, aExpirationDate, aCvc,  this, aCommand);
  }

  public boolean addPayment(Payment aPayment)
  {
    boolean wasAdded = false;
    if (payments.contains(aPayment)) { return false; }
    Customer existingCustomer = aPayment.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aPayment.setCustomer(this);
    }
    else
    {
      payments.add(aPayment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePayment(Payment aPayment)
  {
    boolean wasRemoved = false;
    //Unable to remove aPayment, as it must always have a customer
    if (!this.equals(aPayment.getCustomer()))
    {
      payments.remove(aPayment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPaymentAt(Payment aPayment, int index)
  {  
    boolean wasAdded = false;
    if(addPayment(aPayment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPayments()) { index = numberOfPayments() - 1; }
      payments.remove(aPayment);
      payments.add(index, aPayment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePaymentAt(Payment aPayment, int index)
  {
    boolean wasAdded = false;
    if(payments.contains(aPayment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPayments()) { index = numberOfPayments() - 1; }
      payments.remove(aPayment);
      payments.add(index, aPayment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPaymentAt(aPayment, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCommands()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Command addCommand( String aCommandDate, float aTotalPrice)
  {
    return new Command( aCommandDate, aTotalPrice, this);
  }
  public boolean addCommand(Command aCommand)
  {
    boolean wasAdded = false;
    if (commands.contains(aCommand)) { return false; }
    Customer existingCustomer = aCommand.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aCommand.setCustomer(this);
    }
    else
    {
      commands.add(aCommand);
    }
    wasAdded = true;
    return wasAdded;
  }
  public boolean removeCommand(Command aCommand)
  {
    boolean wasRemoved = false;
    //Unable to remove aCommand, as it must always have a customer
    if (!this.equals(aCommand.getCustomer()))
    {
      commands.remove(aCommand);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCommandAt(Command aCommand, int index)
  {
    boolean wasAdded = false;
    if(addCommand(aCommand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCommands()) { index = numberOfCommands() - 1; }
      commands.remove(aCommand);
      commands.add(index, aCommand);
      wasAdded = true;
    }
    return wasAdded;
  }
  public boolean addOrMoveCommandAt(Command aCommand, int index)
  {
    boolean wasAdded = false;
    if(commands.contains(aCommand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCommands()) { index = numberOfCommands() - 1; }
      commands.remove(aCommand);
      commands.add(index, aCommand);
      wasAdded = true;
    }
    else
    {
      wasAdded = addCommandAt(aCommand, index);
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
    for(int i=payments.size(); i > 0; i--)
    {
      Payment aPayment = payments.get(i - 1);
      aPayment.delete();
    }
    for(int i=commands.size(); i > 0; i--)
    {
      Command aCommand = commands.get(i - 1);
      aCommand.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "shippingAddress" + ":" + getShippingAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "wishlist" + "=" + (getWishlist() != null ? !getWishlist().equals(this)  ? getWishlist().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart" + "=" + (getCart() != null ? !getCart().equals(this)  ? getCart().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
