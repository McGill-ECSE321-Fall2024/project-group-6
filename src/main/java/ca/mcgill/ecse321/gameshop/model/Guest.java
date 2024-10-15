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
  //@GeneratedValue
  private int guestId;

  //Guest Associations
  @OneToOne(cascade = CascadeType.ALL)
  private Cart guestCart;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Guest() {
  }
  public Guest(int aGuestId)
  {
    guestId = aGuestId;
  }


  public Guest(int aGuestId, int aCartIdForGuestCart)
  {
    guestId = aGuestId;
    guestCart = new Cart(aCartIdForGuestCart,  this);
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
