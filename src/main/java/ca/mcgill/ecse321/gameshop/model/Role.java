package ca.mcgill.ecse321.gameshop.model;

//%% NEW FILE Role BEGINS HERE %%

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
    private Person person;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Role(Person aPerson)
    {
        if (!setPerson(aPerson))
        {
            throw new RuntimeException("Unable to create Role due to aPerson. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template association_GetOne */
    public Person getPerson()
    {
        return person;
    }
    /* Code from template association_SetUnidirectionalOne */
    public boolean setPerson(Person aNewPerson)
    {
        boolean wasSet = false;
        if (aNewPerson != null)
        {
            person = aNewPerson;
            wasSet = true;
        }
        return wasSet;
    }

    public void delete()
    {
        person = null;
    }

}