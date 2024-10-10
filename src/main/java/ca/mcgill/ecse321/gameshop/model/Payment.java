package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;

// line 81 "model.ump"
// line 159 "model.ump"
public class Payment
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Payment Attributes
    private String billingAddress;
    private int creditCardNb;
    private Date expirationDate;
    private int cvc;
    private int total;

    //Payment Associations
    private Customer customer;
    private Order order;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Payment(String aBillingAddress, int aCreditCardNb, Date aExpirationDate, int aCvc, int aTotal, Customer aCustomer, Order aOrder)
    {
        billingAddress = aBillingAddress;
        creditCardNb = aCreditCardNb;
        expirationDate = aExpirationDate;
        cvc = aCvc;
        total = aTotal;
        boolean didAddCustomer = setCustomer(aCustomer);
        if (!didAddCustomer)
        {
            throw new RuntimeException("Unable to create payment due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        if (aOrder == null || aOrder.getPayment() != null)
        {
            throw new RuntimeException("Unable to create Payment due to aOrder. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        order = aOrder;
    }

    public Payment(String aBillingAddress, int aCreditCardNb, Date aExpirationDate, int aCvc, int aTotal, Customer aCustomer, int aOrderIdForOrder, Date aOrderDateForOrder, float aTotalPriceForOrder, Cart aCartForOrder)
    {
        billingAddress = aBillingAddress;
        creditCardNb = aCreditCardNb;
        expirationDate = aExpirationDate;
        cvc = aCvc;
        total = aTotal;
        boolean didAddCustomer = setCustomer(aCustomer);
        if (!didAddCustomer)
        {
            throw new RuntimeException("Unable to create payment due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        order = new Order(aOrderIdForOrder, aOrderDateForOrder, aTotalPriceForOrder, this, aCartForOrder);
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

    public boolean setCreditCardNb(int aCreditCardNb)
    {
        boolean wasSet = false;
        creditCardNb = aCreditCardNb;
        wasSet = true;
        return wasSet;
    }

    public boolean setExpirationDate(Date aExpirationDate)
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

    public boolean setTotal(int aTotal)
    {
        boolean wasSet = false;
        total = aTotal;
        wasSet = true;
        return wasSet;
    }

    public String getBillingAddress()
    {
        return billingAddress;
    }

    public int getCreditCardNb()
    {
        return creditCardNb;
    }

    public Date getExpirationDate()
    {
        return expirationDate;
    }

    public int getCvc()
    {
        return cvc;
    }

    public int getTotal()
    {
        return total;
    }
    /* Code from template association_GetOne */
    public Customer getCustomer()
    {
        return customer;
    }
    /* Code from template association_GetOne */
    public Order getOrder()
    {
        return order;
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

    public void delete()
    {
        Customer placeholderCustomer = customer;
        this.customer = null;
        if(placeholderCustomer != null)
        {
            placeholderCustomer.removePayment(this);
        }
        Order existingOrder = order;
        order = null;
        if (existingOrder != null)
        {
            existingOrder.delete();
        }
    }


    public String toString()
    {
        return super.toString() + "["+
                "billingAddress" + ":" + getBillingAddress()+ "," +
                "creditCardNb" + ":" + getCreditCardNb()+ "," +
                "cvc" + ":" + getCvc()+ "," +
                "total" + ":" + getTotal()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "expirationDate" + "=" + (getExpirationDate() != null ? !getExpirationDate().equals(this)  ? getExpirationDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
                "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
    }
}
