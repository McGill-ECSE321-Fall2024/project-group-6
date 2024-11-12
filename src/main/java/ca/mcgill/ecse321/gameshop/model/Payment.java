/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package ca.mcgill.ecse321.gameshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
  @ManyToOne
  private Command command;

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

  }
  public Payment(String aBillingAddress, int aCreditCardNb, String aExpirationDate, int aCvc,  Customer aCustomer, Command aCommand)
  {
    billingAddress = aBillingAddress;
    creditCardNb = aCreditCardNb;
    expirationDate = aExpirationDate;
    cvc = aCvc;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create payment due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCommand = setCommand(aCommand);
    if (!didAddCommand)
    {
      throw new RuntimeException("Unable to create payment due to command. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public Command getCommand()
  {
    return command;
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
  /* Code from template association_SetOneToMany */
  public boolean setCommand(Command aCommand)
  {
    boolean wasSet = false;
    if (aCommand == null)
    {
      return wasSet;
    }

    Command existingCommand = command;
    command = aCommand;
    if (existingCommand != null && !existingCommand.equals(aCommand))
    {
      existingCommand.removePayment(this);
    }
    command.addPayment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removePayment(this);
    }
    Command placeholderCommand = command;
    this.command = null;
    if(placeholderCommand != null)
    {
      placeholderCommand.removePayment(this);
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
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "command = "+(getCommand()!=null?Integer.toHexString(System.identityHashCode(getCommand())):"null");
  }
}