package ca.mcgill.ecse321.gameshop.model;
import java.util.*;
import java.sql.Date;
import jakarta.persistence.*;
// line 17 "model.ump"
// line 109 "model.ump"
@Entity
public class Customer extends Role {

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes

  private String shippingAddress;

  //Customer Associations
  @OneToOne(cascade = CascadeType.ALL)
  private Cart cart;
  @OneToOne (cascade = CascadeType.ALL)
  private Wishlist wishlist;
  @OneToMany
  private List<Review> reviews;
  @OneToMany
  private List<Payment> payments;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Customer(){
      super();

  }
  public Customer(int aRoleId, Person aPerson, String aShippingAddress)
  {
    super(aRoleId, aPerson);
    shippingAddress = aShippingAddress;
    /*
    if (aCart == null || aCart.getCustomer() != null)
    {
      throw new RuntimeException("Unable to create Customer due to aCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cart = aCart;
    if (aWishlist == null || aWishlist.getCustomer() != null)
    {
      throw new RuntimeException("Unable to create Customer due to aWishlist. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    wishlist = aWishlist;

     */
    reviews = new ArrayList<Review>();
    payments = new ArrayList<Payment>();
  }

  public Customer(int aRoleId, Person aPerson, String aShippingAddress, int aCartIdForCart, Guest aGuestForCart, int aWishlistIdForWishlist)
  {
    super(aRoleId, aPerson);
    shippingAddress = aShippingAddress;
    cart = new Cart(aCartIdForCart, this);
    wishlist = new Wishlist(aWishlistIdForWishlist, this);
    reviews = new ArrayList<Review>();
    payments = new ArrayList<Payment>();
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

  public String getShippingAddress()
  {
    return shippingAddress;
  }
  /* Code from template association_GetOne */
  public Cart getCart()
  {
    return cart;
  }
  /* Code from template association_GetOne */
  public Wishlist getWishlist()
  {
    return wishlist;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(Review.StarRating aRating, String aComment, int aAmountOfLikes, int aReviewId, Manager aManager, Game aGame)
  {
    return new Review(aRating, aComment, aAmountOfLikes, aReviewId, this, aManager, aGame);
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
  public Payment addPayment(String aBillingAddress, int aCreditCardNb, String aExpirationDate, int aCvc, int aTotal, int aPaymentId, Command aCommand)
  {
    return new Payment(aBillingAddress, aCreditCardNb, aExpirationDate, aCvc, aTotal, aPaymentId, this, aCommand);
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

  public void delete()
  {
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null)
    {
      existingCart.delete();
    }
    Wishlist existingWishlist = wishlist;
    wishlist = null;
    if (existingWishlist != null)
    {
      existingWishlist.delete();
    }
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
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "shippingAddress" + ":" + getShippingAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "wishlist = "+(getWishlist()!=null?Integer.toHexString(System.identityHashCode(getWishlist())):"null");
  }
}


