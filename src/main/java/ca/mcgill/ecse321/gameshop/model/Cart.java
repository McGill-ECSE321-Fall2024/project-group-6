package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 78 "model.ump"
// line 151 "model.ump"
public class Cart
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Cart Associations
    private List<Game> games;
    private List<Order> orders;
    private Customer customer;
    private Guest guest;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Cart(Customer aCustomer, Guest aGuest)
    {
        games = new ArrayList<Game>();
        orders = new ArrayList<Order>();
        if (aCustomer == null || aCustomer.getCarts() != null)
        {
            throw new RuntimeException("Unable to create Cart due to aCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        customer = aCustomer;
        boolean didAddGuest = setGuest(aGuest);
        if (!didAddGuest)
        {
            throw new RuntimeException("Unable to create guestCart due to guest. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    public Cart(Person aPersonForCustomer, String aShippingAddressForCustomer, Wishlist aWishlistForCustomer, Guest aGuest)
    {
        games = new ArrayList<Game>();
        orders = new ArrayList<Order>();
        customer = new Customer(aPersonForCustomer, aShippingAddressForCustomer, this, aWishlistForCustomer);
        boolean didAddGuest = setGuest(aGuest);
        if (!didAddGuest)
        {
            throw new RuntimeException("Unable to create guestCart due to guest. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template association_GetMany */
    public Game getGame(int index)
    {
        Game aGame = games.get(index);
        return aGame;
    }

    public List<Game> getGames()
    {
        List<Game> newGames = Collections.unmodifiableList(games);
        return newGames;
    }

    public int numberOfGames()
    {
        int number = games.size();
        return number;
    }

    public boolean hasGames()
    {
        boolean has = games.size() > 0;
        return has;
    }

    public int indexOfGame(Game aGame)
    {
        int index = games.indexOf(aGame);
        return index;
    }
    /* Code from template association_GetMany */
    public Order getOrder(int index)
    {
        Order aOrder = orders.get(index);
        return aOrder;
    }

    public List<Order> getOrders()
    {
        List<Order> newOrders = Collections.unmodifiableList(orders);
        return newOrders;
    }

    public int numberOfOrders()
    {
        int number = orders.size();
        return number;
    }

    public boolean hasOrders()
    {
        boolean has = orders.size() > 0;
        return has;
    }

    public int indexOfOrder(Order aOrder)
    {
        int index = orders.indexOf(aOrder);
        return index;
    }
    /* Code from template association_GetOne */
    public Customer getCustomer()
    {
        return customer;
    }
    /* Code from template association_GetOne */
    public Guest getGuest()
    {
        return guest;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfGames()
    {
        return 0;
    }
    /* Code from template association_AddManyToManyMethod */
    public boolean addGame(Game aGame)
    {
        boolean wasAdded = false;
        if (games.contains(aGame)) { return false; }
        games.add(aGame);
        if (aGame.indexOfCart(this) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = aGame.addCart(this);
            if (!wasAdded)
            {
                games.remove(aGame);
            }
        }
        return wasAdded;
    }
    /* Code from template association_RemoveMany */
    public boolean removeGame(Game aGame)
    {
        boolean wasRemoved = false;
        if (!games.contains(aGame))
        {
            return wasRemoved;
        }

        int oldIndex = games.indexOf(aGame);
        games.remove(oldIndex);
        if (aGame.indexOfCart(this) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = aGame.removeCart(this);
            if (!wasRemoved)
            {
                games.add(oldIndex,aGame);
            }
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addGameAt(Game aGame, int index)
    {
        boolean wasAdded = false;
        if(addGame(aGame))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfGames()) { index = numberOfGames() - 1; }
            games.remove(aGame);
            games.add(index, aGame);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveGameAt(Game aGame, int index)
    {
        boolean wasAdded = false;
        if(games.contains(aGame))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfGames()) { index = numberOfGames() - 1; }
            games.remove(aGame);
            games.add(index, aGame);
            wasAdded = true;
        }
        else
        {
            wasAdded = addGameAt(aGame, index);
        }
        return wasAdded;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfOrders()
    {
        return 0;
    }
    /* Code from template association_AddManyToOne */
    public Order addOrder(int aOrderId, Date aOrderDate, float aTotalPrice, Payment aPayment)
    {
        return new Order(aOrderId, aOrderDate, aTotalPrice, aPayment, this);
    }

    public boolean addOrder(Order aOrder)
    {
        boolean wasAdded = false;
        if (orders.contains(aOrder)) { return false; }
        Cart existingCart = aOrder.getCart();
        boolean isNewCart = existingCart != null && !this.equals(existingCart);
        if (isNewCart)
        {
            aOrder.setCart(this);
        }
        else
        {
            orders.add(aOrder);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeOrder(Order aOrder)
    {
        boolean wasRemoved = false;
        //Unable to remove aOrder, as it must always have a cart
        if (!this.equals(aOrder.getCart()))
        {
            orders.remove(aOrder);
            wasRemoved = true;
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addOrderAt(Order aOrder, int index)
    {
        boolean wasAdded = false;
        if(addOrder(aOrder))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
            orders.remove(aOrder);
            orders.add(index, aOrder);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveOrderAt(Order aOrder, int index)
    {
        boolean wasAdded = false;
        if(orders.contains(aOrder))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
            orders.remove(aOrder);
            orders.add(index, aOrder);
            wasAdded = true;
        }
        else
        {
            wasAdded = addOrderAt(aOrder, index);
        }
        return wasAdded;
    }
    /* Code from template association_SetOneToMany */
    public boolean setGuest(Guest aGuest)
    {
        boolean wasSet = false;
        if (aGuest == null)
        {
            return wasSet;
        }

        Guest existingGuest = guest;
        guest = aGuest;
        if (existingGuest != null && !existingGuest.equals(aGuest))
        {
            existingGuest.removeGuestCart(this);
        }
        guest.addGuestCart(this);
        wasSet = true;
        return wasSet;
    }

    public void delete()
    {
        ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
        games.clear();
        for(Game aGame : copyOfGames)
        {
            aGame.removeCart(this);
        }
        for(int i=orders.size(); i > 0; i--)
        {
            Order aOrder = orders.get(i - 1);
            aOrder.delete();
        }
        Customer existingCustomer = customer;
        customer = null;
        if (existingCustomer != null)
        {
            existingCustomer.delete();
        }
        Guest placeholderGuest = guest;
        this.guest = null;
        if(placeholderGuest != null)
        {
            placeholderGuest.removeGuestCart(this);
        }
    }

}