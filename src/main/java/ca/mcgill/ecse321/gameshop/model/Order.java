package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;

// line 37 "model.ump"
// line 124 "model.ump"
public class Order
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Order Attributes
    private int orderId;
    private Date orderDate;
    private float totalPrice;

    //Order Associations
    private Payment payment;
    private Cart cart;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Order(int aOrderId, Date aOrderDate, float aTotalPrice, Payment aPayment, Cart aCart)
    {
        orderId = aOrderId;
        orderDate = aOrderDate;
        totalPrice = aTotalPrice;
        if (aPayment == null || aPayment.getOrder() != null)
        {
            throw new RuntimeException("Unable to create Order due to aPayment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        payment = aPayment;
        boolean didAddCart = setCart(aCart);
        if (!didAddCart)
        {
            throw new RuntimeException("Unable to create order due to cart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    public Order(int aOrderId, Date aOrderDate, float aTotalPrice, String aBillingAddressForPayment, int aCreditCardNbForPayment, Date aExpirationDateForPayment, int aCvcForPayment, int aTotalForPayment, Customer aCustomerForPayment, Cart aCart)
    {
        orderId = aOrderId;
        orderDate = aOrderDate;
        totalPrice = aTotalPrice;
        payment = new Payment(aBillingAddressForPayment, aCreditCardNbForPayment, aExpirationDateForPayment, aCvcForPayment, aTotalForPayment, aCustomerForPayment, this);
        boolean didAddCart = setCart(aCart);
        if (!didAddCart)
        {
            throw new RuntimeException("Unable to create order due to cart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setOrderId(int aOrderId)
    {
        boolean wasSet = false;
        orderId = aOrderId;
        wasSet = true;
        return wasSet;
    }

    public boolean setOrderDate(Date aOrderDate)
    {
        boolean wasSet = false;
        orderDate = aOrderDate;
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

    public int getOrderId()
    {
        return orderId;
    }

    public Date getOrderDate()
    {
        return orderDate;
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
            existingCart.removeOrder(this);
        }
        cart.addOrder(this);
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
            placeholderCart.removeOrder(this);
        }
    }


    public String toString()
    {
        return super.toString() + "["+
                "orderId" + ":" + getOrderId()+ "," +
                "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "orderDate" + "=" + (getOrderDate() != null ? !getOrderDate().equals(this)  ? getOrderDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "payment = "+(getPayment()!=null?Integer.toHexString(System.identityHashCode(getPayment())):"null") + System.getProperties().getProperty("line.separator") +
                "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null");
    }
}

