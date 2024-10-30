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
  @OneToMany
  private List<Payment> payments;

  //------------------------
  // CONSTRUCTOR
  //------------------------
public Command (){

}
  public Command( String aCommandDate, float aTotalPrice)
  {
    commandDate = aCommandDate;
    totalPrice = aTotalPrice;
    payments = new ArrayList<Payment>();
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
  public static int minimumNumberOfPayments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Payment addPayment(String aBillingAddress, int aCreditCardNb, String aExpirationDate, int aCvc, int aPaymentId, Customer aCustomer)
  {
    return new Payment(aBillingAddress, aCreditCardNb, aExpirationDate, aCvc, aCustomer, this);
  }

  public boolean addPayment(Payment aPayment)
  {
    boolean wasAdded = false;
    if (payments.contains(aPayment)) { return false; }
    Command existingCommand = aPayment.getCommand();
    boolean isNewCommand = existingCommand != null && !this.equals(existingCommand);
    if (isNewCommand)
    {
      aPayment.setCommand(this);
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
    //Unable to remove aPayment, as it must always have a command
    if (!this.equals(aPayment.getCommand()))
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
    for(int i=payments.size(); i > 0; i--)
    {
      Payment aPayment = payments.get(i - 1);
      aPayment.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "commandId" + ":" + getCommandId()+ "," +
            "commandDate" + ":" + getCommandDate()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]";
  }
}