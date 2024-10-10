package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 61 "model.ump"
// line 163 "model.ump"
public class Category
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Category Attributes
    private String categoryName;

    //Category Associations
    private List<Game> games;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Category(String aCategoryName)
    {
        categoryName = aCategoryName;
        games = new ArrayList<Game>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setCategoryName(String aCategoryName)
    {
        boolean wasSet = false;
        categoryName = aCategoryName;
        wasSet = true;
        return wasSet;
    }

    public String getCategoryName()
    {
        return categoryName;
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
        if (aGame.indexOfCategory(this) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = aGame.addCategory(this);
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
        if (aGame.indexOfCategory(this) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = aGame.removeCategory(this);
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
        ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
        games.clear();
        for(Game aGame : copyOfGames)
        {
            if (aGame.numberOfCategories() <= Game.minimumNumberOfCategories())
            {
                aGame.delete();
            }
            else
            {
                aGame.removeCategory(this);
            }
        }
    }


    public String toString()
    {
        return super.toString() + "["+
                "categoryName" + ":" + getCategoryName()+ "]";
    }
}