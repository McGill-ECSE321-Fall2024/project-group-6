package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 33 "model.ump"
// line 119 "model.ump"
public class Manager extends Role
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Manager Associations
    private List<Game> games;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Manager(User aUser)
    {
        super(aUser);
        games = new ArrayList<Game>();
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
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfGames()
    {
        return 0;
    }
    /* Code from template association_AddManyToOne */
    public Game addGame(String aName, String aDescription, float aPrice, int aStockQuantity, boolean aApprovedByOwner, Image aPhoto, String aCategory, Category... allCategories)
    {
        return new Game(aName, aDescription, aPrice, aStockQuantity, aApprovedByOwner, aPhoto, aCategory, this, allCategories);
    }

    public boolean addGame(Game aGame)
    {
        boolean wasAdded = false;
        if (games.contains(aGame)) { return false; }
        Manager existingManager = aGame.getManager();
        boolean isNewManager = existingManager != null && !this.equals(existingManager);
        if (isNewManager)
        {
            aGame.setManager(this);
        }
        else
        {
            games.add(aGame);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeGame(Game aGame)
    {
        boolean wasRemoved = false;
        //Unable to remove aGame, as it must always have a manager
        if (!this.equals(aGame.getManager()))
        {
            games.remove(aGame);
            wasRemoved = true;
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
        for(int i=games.size(); i > 0; i--)
        {
            Game aGame = games.get(i - 1);
            aGame.delete();
        }
        super.delete();
    }

}
