package ca.mcgill.ecse321.gameshop.model;
import jakarta.persistence.*;
import java.util.*;
import jakarta.persistence.*;
// line 47 "model.ump"
// line 174 "model.ump"
@Entity
public class Guest
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Guest Attributes
  @Id
  @GeneratedValue
  private int guestId;

  //Guest Associations
  @OneToOne(cascade = CascadeType.ALL)
  private Cart guestCart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Guest(int aGuestId, Cart aGuestCart)
  {
    guestId = aGuestId;
    if (aGuestCart == null || aGuestCart.getGuest() != null)
    {
      throw new RuntimeException("Unable to create Guest due to aGuestCart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    guestCart = aGuestCart;
  }

  public Guest(int aGuestId, int aCartIdForGuestCart, Customer aCustomerForGuestCart)
  {
    guestId = aGuestId;
    guestCart = new Cart(aCartIdForGuestCart, aCustomerForGuestCart, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGuestId(int aGuestId)
  {
    boolean wasSet = false;
    guestId = aGuestId;
    wasSet = true;
    return wasSet;
  }

  public int getGuestId()
  {
    return guestId;
  }
  /* Code from template association_GetOne */
  public Cart getGuestCart()
  {
    return guestCart;
  }

  public void delete()
  {
    Cart existingGuestCart = guestCart;
    guestCart = null;
    if (existingGuestCart != null)
    {
      existingGuestCart.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "guestId" + ":" + getGuestId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "guestCart = "+(getGuestCart()!=null?Integer.toHexString(System.identityHashCode(getGuestCart())):"null");
  }
}
