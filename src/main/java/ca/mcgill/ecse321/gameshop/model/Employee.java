package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



// line 28 "model.ump"
// line 114 "model.ump"
public class Employee extends Role
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Employee Attributes
    private String assignedTasks;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Employee(User aUser, String aAssignedTasks)
    {
        super(aUser);
        assignedTasks = aAssignedTasks;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setAssignedTasks(String aAssignedTasks)
    {
        boolean wasSet = false;
        assignedTasks = aAssignedTasks;
        wasSet = true;
        return wasSet;
    }

    public String getAssignedTasks()
    {
        return assignedTasks;
    }

    public void delete()
    {
        super.delete();
    }


    public String toString()
    {
        return super.toString() + "["+
                "assignedTasks" + ":" + getAssignedTasks()+ "]";
    }
}
