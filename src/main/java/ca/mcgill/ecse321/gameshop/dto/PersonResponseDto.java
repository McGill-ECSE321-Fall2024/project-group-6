

package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Person;

public class PersonResponseDto {
    //Person Attributes
    private String username;
    private String email;
    private String phone;
    private int userId;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private PersonResponseDto() {
    }

    public PersonResponseDto(Person person) {
        this.username = person.getUsername();
        this.email = person.getEmail();
        this.phone = person.getPhone();
        this.userId = person.getUserId();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getUserId() {
        return userId;
    }
}
