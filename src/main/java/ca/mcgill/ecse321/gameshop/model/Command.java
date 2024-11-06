/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/
package ca.mcgill.ecse321.gameshop.model;
import java.util.*;


import jakarta.persistence.*;



/**
 * Command class
 */
// line 45 "model.ump"
// line 128 "model.ump"
  @Entity
public class Command
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Command Attributes
  @Id
  @GeneratedValue
  private int commandId;
  private String commandDate;
  private float totalPrice;

  //Command Associations
  @ManyToOne
  private Payment payment;
@ManyToOne
  private Customer customer;
  //------------------------
  // CONSTRUCTOR
  //------------------------
public Command (){

}
  public Command( String aCommandDate, float aTotalPrice)
  {
    commandDate = aCommandDate;
    totalPrice = aTotalPrice;
    //payments = new ArrayList<Payment>();
  }
  public Command( String aCommandDate, float aTotalPrice, Customer aCustomer)
  {
    //commandId = aCommandId;
    commandDate = aCommandDate;
    totalPrice = aTotalPrice;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create command due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCommandId(int aCommandId)
  {
    boolean wasSet = false;
    commandId = aCommandId;
    wasSet = true;
    return wasSet;
  }

  public boolean setCommandDate(String aCommandDate)
  {
    boolean wasSet = false;
    commandDate = aCommandDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalPrice(float aTotalPrice)
  {
    boolean wasSet = false;
    totalPrice = aTotalPrice;
    wasSet = true;
    return wasSet;
  }

  public int getCommandId()
  {
    return commandId;
  }

  public String getCommandDate()
  {
    return commandDate;
  }

  public float getTotalPrice()
  {
    return totalPrice;
  }
  /* Code from template association_GetOne */
  public Payment getPayment()
  {
    return payment;
  }

  public boolean hasPayment()
  {
    boolean has = payment != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setPayment(Payment aPayment)
  {
    boolean wasSet = false;
    Payment existingPayment = payment;
    payment = aPayment;
    if (existingPayment != null && !existingPayment.equals(aPayment))
    {
      existingPayment.removeCommand(this);
    }
    if (aPayment != null)
    {
      aPayment.addCommand(this);
    }
    wasSet = true;
    return wasSet;
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
      existingCustomer.removeCommand(this);
    }
    customer.addCommand(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (payment != null)
    {
      Payment placeholderPayment = payment;
      this.payment = null;
      placeholderPayment.removeCommand(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeCommand(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "commandId" + ":" + getCommandId()+ "," +
            "commandDate" + ":" + getCommandDate()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "payment = "+(getPayment()!=null?Integer.toHexString(System.identityHashCode(getPayment())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}