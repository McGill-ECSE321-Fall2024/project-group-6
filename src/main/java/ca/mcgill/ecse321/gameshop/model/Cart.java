package ca.mcgill.ecse321.gameshop.model;
import java.util.*;
import java.sql.Date;
import jakarta.persistence.*;
// line 81 "model.ump"
// line 156 "model.ump"

@Entity
public class Cart
{
    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Cart Attributes
    @Id
    //@GeneratedValue
    private int cartId;

    //Cart Associations
    @ManyToMany
    private List<Game> games;
    @OneToMany
    private List<Command> commands;
    @OneToOne
    private Customer customer;
    @OneToOne
    private Guest guest;

    //------------------------
    // CONSTRUCTOR
    //------------------------
    public Cart() {
    }
    public Cart(int aCartId, Customer aCustomer)
    {
        cartId = aCartId;
        games = new ArrayList<Game>();
        commands = new ArrayList<Command>();

        if (aCustomer == null || aCustomer.getCart() != null)
        {
            throw new RuntimeException("Unable to create Cart due to aCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        customer = aCustomer;

    }
    public Cart(int aCartId,Guest aGuest)
    {
        cartId = aCartId;
        games = new ArrayList<Game>();
        commands = new ArrayList<Command>();

        if (aGuest == null || aGuest.getGuestCart() != null)
        {
            throw new RuntimeException("Unable to create Cart due to aGuest. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }


        guest = aGuest;
    }

    public Cart(int aCartId, int aRoleIdForCustomer, Person aPersonForCustomer, String aShippingAddressForCustomer, int aGuestIdForGuest)
    {
        cartId = aCartId;
        games = new ArrayList<Game>();
        commands = new ArrayList<Command>();
        customer = new Customer(aRoleIdForCustomer, aPersonForCustomer, aShippingAddressForCustomer);
        guest = new Guest(aGuestIdForGuest);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setCartId(int aCartId)
    {
        boolean wasSet = false;
        cartId = aCartId;
        wasSet = true;
        return wasSet;
    }

    public int getCartId()
    {
        return cartId;
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
    public static int minimumNumberOfCommands()
    {
        return 0;
    }
    /* Code from template association_AddManyToOne */
    public Command addCommand(int aCommandId, String aCommandDate, float aTotalPrice, Payment aPayment)
    {
        return new Command(aCommandId, aCommandDate, aTotalPrice, aPayment, this);
    }

    public boolean addCommand(Command aCommand)
    {
        boolean wasAdded = false;
        if (commands.contains(aCommand)) { return false; }
        Cart existingCart = aCommand.getCart();
        boolean isNewCart = existingCart != null && !this.equals(existingCart);
        if (isNewCart)
        {
            aCommand.setCart(this);
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
        //Unable to remove aCommand, as it must always have a cart
        if (!this.equals(aCommand.getCart()))
        {
            commands.remove(aCommand);
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
        ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
        games.clear();
        for(Game aGame : copyOfGames)
        {
            aGame.removeCart(this);
        }
        for(int i=commands.size(); i > 0; i--)
        {
            Command aCommand = commands.get(i - 1);
            aCommand.delete();
        }
        Customer existingCustomer = customer;
        customer = null;
        if (existingCustomer != null)
        {
            existingCustomer.delete();
        }
        Guest existingGuest = guest;
        guest = null;
        if (existingGuest != null)
        {
            existingGuest.delete();
        }
    }


    public String toString()
    {
        return super.toString() + "["+
                "cartId" + ":" + getCartId()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
                "  " + "guest = "+(getGuest()!=null?Integer.toHexString(System.identityHashCode(getGuest())):"null");
    }

}
