package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 63 "model.ump"
// line 138 "model.ump"
public class Wishlist
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Wishlist Attributes
    private List<Game> wishlistGames;

    //Wishlist Associations
    private Customer customer;
    private List<Game> games;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Wishlist(List<Game> aWishlistGames, Customer aCustomer)
    {
        wishlistGames = aWishlistGames;
        if (aCustomer == null || aCustomer.getWishlist() != null)
        {
            throw new RuntimeException("Unable to create Wishlist due to aCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        customer = aCustomer;
        games = new ArrayList<Game>();
    }

    public Wishlist(List<Game> aWishlistGames, User aUserForCustomer, String aShippingAddressForCustomer, Cart aCartsForCustomer)
    {
        wishlistGames = aWishlistGames;
        customer = new Customer(aUserForCustomer, aShippingAddressForCustomer, aCartsForCustomer, this);
        games = new ArrayList<Game>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setWishlistGames(List<Game> aWishlistGames)
    {
        boolean wasSet = false;
        wishlistGames = aWishlistGames;
        wasSet = true;
        return wasSet;
    }

    public List<Game> getWishlistGames()
    {
        return wishlistGames;
    }
    /* Code from template association_GetOne */
    public Customer getCustomer()
    {
        return customer;
    }
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
        if (aGame.indexOfWishlist(this) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = aGame.addWishlist(this);
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
        if (aGame.indexOfWishlist(this) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = aGame.removeWishlist(this);
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

    public void delete()
    {
        Customer existingCustomer = customer;
        customer = null;
        if (existingCustomer != null)
        {
            existingCustomer.delete();
        }
        ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
        games.clear();
        for(Game aGame : copyOfGames)
        {
            aGame.removeWishlist(this);
        }
    }


    public String toString()
    {
        return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "wishlistGames" + "=" + (getWishlistGames() != null ? !getWishlistGames().equals(this)  ? getWishlistGames().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
    }
}