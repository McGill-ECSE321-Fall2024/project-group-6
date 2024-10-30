package ca.mcgill.ecse321.gameshop.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/
import java.util.*;
import jakarta.persistence.*;

import java.sql.Date;

/**
 * Command class--> the orders being made by customers
 */
// line 42 "model.ump"
// line 143 "model.ump"
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
    @OneToOne
    private Payment payment;
    @ManyToOne
    private Cart cart;

    //------------------------
    // CONSTRUCTOR
    //------------------------
public Command(){

}
    public Command( String aCommandDate, float aTotalPrice, Payment aPayment, Cart aCart)
    {

        commandDate = aCommandDate;
        totalPrice = aTotalPrice;
        if (aPayment == null || aPayment.getCommand() != null)
        {
            throw new RuntimeException("Unable to create Command due to aPayment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        payment = aPayment;
        boolean didAddCart = setCart(aCart);
        if (!didAddCart)
        {
            throw new RuntimeException("Unable to create command due to cart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    public Command( String aCommandDate, float aTotalPrice, String aBillingAddressForPayment, int aCreditCardNbForPayment, String aExpirationDateForPayment, int aCvcForPayment, int aTotalForPayment,  Customer aCustomerForPayment, Cart aCart)
    {

        commandDate = aCommandDate;
        totalPrice = aTotalPrice;
        payment = new Payment(aBillingAddressForPayment, aCreditCardNbForPayment, aExpirationDateForPayment, aCvcForPayment, aTotalForPayment,  aCustomerForPayment, this);
        boolean didAddCart = setCart(aCart);
        if (!didAddCart)
        {
            throw new RuntimeException("Unable to create command due to cart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------



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
    /* Code from template association_GetOne */
    public Cart getCart()
    {
        return cart;
    }
    /* Code from template association_SetOneToMany */
    public boolean setCart(Cart aCart)
    {
        boolean wasSet = false;
        if (aCart == null)
        {
            return wasSet;
        }

        Cart existingCart = cart;
        cart = aCart;
        if (existingCart != null && !existingCart.equals(aCart))
        {
            existingCart.removeCommand(this);
        }
        cart.addCommand(this);
        wasSet = true;
        return wasSet;
    }

    public void delete()
    {
        Payment existingPayment = payment;
        payment = null;
        if (existingPayment != null)
        {
            existingPayment.delete();
        }
        Cart placeholderCart = cart;
        this.cart = null;
        if(placeholderCart != null)
        {
            placeholderCart.removeCommand(this);
        }
    }


    public String toString()
    {
        return super.toString() + "["+
                "commandId" + ":" + getCommandId()+ "," +
                "commandDate" + ":" + getCommandDate()+ "," +
                "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "payment = "+(getPayment()!=null?Integer.toHexString(System.identityHashCode(getPayment())):"null") + System.getProperties().getProperty("line.separator") +
                "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null");
    }
}