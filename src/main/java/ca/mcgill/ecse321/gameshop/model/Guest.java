package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 46 "model.ump"
// line 170 "model.ump"
public class Guest
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Guest Associations
    private List<Cart> guestCart;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Guest()
    {
        guestCart = new ArrayList<Cart>();
    }

    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template association_GetMany */
    public Cart getGuestCart(int index)
    {
        Cart aGuestCart = guestCart.get(index);
        return aGuestCart;
    }

    public List<Cart> getGuestCart()
    {
        List<Cart> newGuestCart = Collections.unmodifiableList(guestCart);
        return newGuestCart;
    }

    public int numberOfGuestCart()
    {
        int number = guestCart.size();
        return number;
    }

    public boolean hasGuestCart()
    {
        boolean has = guestCart.size() > 0;
        return has;
    }

    public int indexOfGuestCart(Cart aGuestCart)
    {
        int index = guestCart.indexOf(aGuestCart);
        return index;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfGuestCart()
    {
        return 0;
    }
    /* Code from template association_AddManyToOne */
    public Cart addGuestCart(Customer aCustomer)
    {
        return new Cart(aCustomer, this);
    }

    public boolean addGuestCart(Cart aGuestCart)
    {
        boolean wasAdded = false;
        if (guestCart.contains(aGuestCart)) { return false; }
        Guest existingGuest = aGuestCart.getGuest();
        boolean isNewGuest = existingGuest != null && !this.equals(existingGuest);
        if (isNewGuest)
        {
            aGuestCart.setGuest(this);
        }
        else
        {
            guestCart.add(aGuestCart);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeGuestCart(Cart aGuestCart)
    {
        boolean wasRemoved = false;
        //Unable to remove aGuestCart, as it must always have a guest
        if (!this.equals(aGuestCart.getGuest()))
        {
            guestCart.remove(aGuestCart);
            wasRemoved = true;
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addGuestCartAt(Cart aGuestCart, int index)
    {
        boolean wasAdded = false;
        if(addGuestCart(aGuestCart))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfGuestCart()) { index = numberOfGuestCart() - 1; }
            guestCart.remove(aGuestCart);
            guestCart.add(index, aGuestCart);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveGuestCartAt(Cart aGuestCart, int index)
    {
        boolean wasAdded = false;
        if(guestCart.contains(aGuestCart))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfGuestCart()) { index = numberOfGuestCart() - 1; }
            guestCart.remove(aGuestCart);
            guestCart.add(index, aGuestCart);
            wasAdded = true;
        }
        else
        {
            wasAdded = addGuestCartAt(aGuestCart, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        while (guestCart.size() > 0)
        {
            Cart aGuestCart = guestCart.get(guestCart.size() - 1);
            aGuestCart.delete();
            guestCart.remove(aGuestCart);
        }

    }

}