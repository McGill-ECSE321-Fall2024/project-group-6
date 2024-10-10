package ca.mcgill.ecse321.gameshop.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

/**
 * Defining the User class
 */
// line 3 "model.ump"
// line 91 "model.ump"
public class User
{

    //------------------------
    // STATIC VARIABLES
    //------------------------

    private static Map<String, User> usersByEmail = new HashMap<String, User>();

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //User Attributes
    private String username;
    private String email;
    private String password;
    private String phone;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public User(String aUsername, String aEmail, String aPassword, String aPhone)
    {
        username = aUsername;
        password = aPassword;
        phone = aPhone;
        if (!setEmail(aEmail))
        {
            throw new RuntimeException("Cannot create due to duplicate email. See https://manual.umple.org?RE003ViolationofUniqueness.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setUsername(String aUsername)
    {
        boolean wasSet = false;
        username = aUsername;
        wasSet = true;
        return wasSet;
    }

    public boolean setEmail(String aEmail)
    {
        boolean wasSet = false;
        String anOldEmail = getEmail();
        if (anOldEmail != null && anOldEmail.equals(aEmail)) {
            return true;
        }
        if (hasWithEmail(aEmail)) {
            return wasSet;
        }
        email = aEmail;
        wasSet = true;
        if (anOldEmail != null) {
            usersByEmail.remove(anOldEmail);
        }
        usersByEmail.put(aEmail, this);
        return wasSet;
    }

    public boolean setPassword(String aPassword)
    {
        boolean wasSet = false;
        password = aPassword;
        wasSet = true;
        return wasSet;
    }

    public boolean setPhone(String aPhone)
    {
        boolean wasSet = false;
        phone = aPhone;
        wasSet = true;
        return wasSet;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }
    /* Code from template attribute_GetUnique */
    public static User getWithEmail(String aEmail)
    {
        return usersByEmail.get(aEmail);
    }
    /* Code from template attribute_HasUnique */
    public static boolean hasWithEmail(String aEmail)
    {
        return getWithEmail(aEmail) != null;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPhone()
    {
        return phone;
    }

    public void delete()
    {
        usersByEmail.remove(getEmail());
    }


    public String toString()
    {
        return super.toString() + "["+
                "username" + ":" + getUsername()+ "," +
                "email" + ":" + getEmail()+ "," +
                "password" + ":" + getPassword()+ "," +
                "phone" + ":" + getPhone()+ "]";
    }
}
