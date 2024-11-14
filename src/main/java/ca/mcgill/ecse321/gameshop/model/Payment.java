/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package ca.mcgill.ecse321.gameshop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Payment class
 */
// line 92 "model.ump"

@Entity

public class Payment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Payment Attributes
  private String billingAddress;
  private long creditCardNb;
  private String expirationDate;
  private int cvc;
  @Id
  @GeneratedValue
  private int paymentId;

  //Payment Associations
  @ManyToOne
  private Customer customer;
  @OneToMany
  private List<Command> commands;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Payment(){

  }
  public Payment(String aBillingAddress, long aCreditCardNb, String aExpirationDate, int aCvc)
  {
    billingAddress = aBillingAddress;
    creditCardNb = aCreditCardNb;
    expirationDate = aExpirationDate;
    cvc = aCvc;
    commands = new ArrayList<Command>();

  }
  public Payment(String aBillingAddress, long aCreditCardNb, String aExpirationDate, int aCvc,  Customer aCustomer)
  {
    billingAddress = aBillingAddress;
    creditCardNb = aCreditCardNb;
    expirationDate = aExpirationDate;
    cvc = aCvc;
    // paymentId = aPaymentId;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create payment due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    commands = new ArrayList<Command>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBillingAddress(String aBillingAddress)
  {
    boolean wasSet = false;
    billingAddress = aBillingAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreditCardNb(long aCreditCardNb)
  {
    boolean wasSet = false;
    creditCardNb = aCreditCardNb;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationDate(String aExpirationDate)
  {
    boolean wasSet = false;
    expirationDate = aExpirationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCvc(int aCvc)
  {
    boolean wasSet = false;
    cvc = aCvc;
    wasSet = true;
    return wasSet;
  }

  public boolean setPaymentId(int aPaymentId)
  {
    boolean wasSet = false;
    paymentId = aPaymentId;
    wasSet = true;
    return wasSet;
  }

  public String getBillingAddress()
  {
    return billingAddress;
  }

  public long getCreditCardNb()
  {
    return creditCardNb;
  }

  public String getExpirationDate()
  {
    return expirationDate;
  }

  public int getCvc()
  {
    return cvc;
  }

  public int getPaymentId()
  {
    return paymentId;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetMany */
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
      existingCustomer.removePayment(this);
    }
    customer.addPayment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCommands()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addCommand(Command aCommand)
  {
    boolean wasAdded = false;
    if (commands.contains(aCommand)) { return false; }
    Payment existingPayment = aCommand.getPayment();
    if (existingPayment == null)
    {
      aCommand.setPayment(this);
    }
    else if (!this.equals(existingPayment))
    {
      existingPayment.removeCommand(aCommand);
      addCommand(aCommand);
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
    if (commands.contains(aCommand))
    {
      commands.remove(aCommand);
      aCommand.setPayment(null);
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
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removePayment(this);
    }
    while( !commands.isEmpty() )
    {
      commands.get(0).setPayment(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "billingAddress" + ":" + getBillingAddress()+ "," +
            "creditCardNb" + ":" + getCreditCardNb()+ "," +
            "expirationDate" + ":" + getExpirationDate()+ "," +
            "cvc" + ":" + getCvc()+ "," +
            "paymentId" + ":" + getPaymentId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }

}
