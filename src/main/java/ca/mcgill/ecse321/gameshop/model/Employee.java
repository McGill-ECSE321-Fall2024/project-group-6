/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321.gameshop.model;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.*;

/**
 * Employee class extending Role
 */
// line 30 "model.ump"
// line 118 "model.ump"
@Entity
public class Employee extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private List <String> assignedTasks;
  private boolean activated;

  //Employee Associations
  @OneToMany
  private List<Game> created;

  //------------------------
  // CONSTRUCTOR
  //------------------------
public Employee(){

}
  public Employee( Person aPerson, List<String> aAssignedTasks)
  {
    super(aPerson);
    assignedTasks = aAssignedTasks;
    created = new ArrayList<Game>();
  }
  public Employee( Person aPerson, boolean aActivated)
  {
    super( aPerson);

    activated = aActivated;
    created = new ArrayList<Game>();
  }
  public Employee( Person aPerson, List<String> aAssignedTasks, boolean aActivated)
  {
    super(aPerson);
    assignedTasks = aAssignedTasks;
    activated = aActivated;
    created = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAssignedTasks(List<String> aAssignedTasks)
  {
    boolean wasSet = false;
    assignedTasks = aAssignedTasks;
    wasSet = true;
    return wasSet;
  }


  public boolean setActivated(boolean aActivated)
  {
    boolean wasSet = false;
    activated = aActivated;
    wasSet = true;
    return wasSet;
  }

  public List<String> getAssignedTasks()
  {
    return assignedTasks;
  }

  public boolean getActivated()
  {
    return activated;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isActivated()
  {
    return activated;
  }
  /* Code from template association_GetMany */
  public Game getCreated(int index)
  {
    Game aCreated = created.get(index);
    return aCreated;
  }

  public List<Game> getCreated()
  {
    List<Game> newCreated = Collections.unmodifiableList(created);
    return newCreated;
  }

  public int numberOfCreated()
  {
    int number = created.size();
    return number;
  }

  public boolean hasCreated()
  {
    boolean has = created.size() > 0;
    return has;
  }

  public int indexOfCreated(Game aCreated)
  {
    int index = created.indexOf(aCreated);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCreated()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addCreated(String aName, String aDescription, float aPrice, int aStockQuantity, String aPhotoURL, int aGameId, boolean aToBeAdded, boolean aToBeRemoved, Manager aManager, List<Category> allCategories)
  {
    return new Game(aName, aDescription, aPrice, aStockQuantity, aPhotoURL,  aToBeAdded, aToBeRemoved, aManager, this, allCategories);
  }

  public boolean addCreated(Game aCreated)
  {
    boolean wasAdded = false;
    if (created.contains(aCreated)) { return false; }
    Employee existingCreator = aCreated.getCreator();
    boolean isNewCreator = existingCreator != null && !this.equals(existingCreator);
    if (isNewCreator)
    {
      aCreated.setCreator(this);
    }
    else
    {
      created.add(aCreated);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCreated(Game aCreated)
  {
    boolean wasRemoved = false;
    //Unable to remove aCreated, as it must always have a creator
    if (!this.equals(aCreated.getCreator()))
    {
      created.remove(aCreated);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCreatedAt(Game aCreated, int index)
  {  
    boolean wasAdded = false;
    if(addCreated(aCreated))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCreated()) { index = numberOfCreated() - 1; }
      created.remove(aCreated);
      created.add(index, aCreated);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCreatedAt(Game aCreated, int index)
  {
    boolean wasAdded = false;
    if(created.contains(aCreated))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCreated()) { index = numberOfCreated() - 1; }
      created.remove(aCreated);
      created.add(index, aCreated);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCreatedAt(aCreated, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=created.size(); i > 0; i--)
    {
      Game aCreated = created.get(i - 1);
      aCreated.delete();
    }
    super.delete();
  }



  public String toString()
  {
    return super.toString() + "["+
            "activated" + ":" + getActivated()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "assignedTasks" + "=" + (getAssignedTasks() != null ? !getAssignedTasks().equals(this)  ? getAssignedTasks().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
