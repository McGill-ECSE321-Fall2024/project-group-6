package ca.mcgill.ecse321.gameshop.model;


import jakarta.persistence.*;

/**
 * Abstract Role class
 */
// line 12 "model.ump"
// line 101 "model.ump"
@MappedSuperclass
public abstract class Role {

  @Id
  @GeneratedValue
  private int roleId;
  //Role Associations
  @ManyToOne
  private Person person;
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Role(int aRoleId, Person aPerson)
  {
    roleId = aRoleId;
    if (!setPerson(aPerson))
    {
      throw new RuntimeException("Unable to create Role due to aPerson. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoleId(int aRoleId)
  {
    boolean wasSet = false;
    roleId = aRoleId;
    wasSet = true;
    return wasSet;
  }

  public int getRoleId()
  {
    return roleId;
  }
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


  public String toString()
  {
    return super.toString() + "["+
            "roleId" + ":" + getRoleId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "person = "+(getPerson()!=null?Integer.toHexString(System.identityHashCode(getPerson())):"null");
  }
}
