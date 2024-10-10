package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



/**
 * Abstract Role class
 */
// line 11 "model.ump"
// line 96 "model.ump"
public abstract class Role
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Role Associations
    private User user;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Role(User aUser)
    {
        if (!setUser(aUser))
        {
            throw new RuntimeException("Unable to create Role due to aUser. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template association_GetOne */
    public User getUser()
    {
        return user;
    }
    /* Code from template association_SetUnidirectionalOne */
    public boolean setUser(User aNewUser)
    {
        boolean wasSet = false;
        if (aNewUser != null)
        {
            user = aNewUser;
            wasSet = true;
        }
        return wasSet;
    }

    public void delete()
    {
        user = null;
    }

}