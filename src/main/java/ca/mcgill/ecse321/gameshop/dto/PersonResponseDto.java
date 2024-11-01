package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Person;

public class PersonResponseDto {
    //Person Attributes
    private String username;
    private String email;
    private String password;
    private String phone;
    private int userId;

    @SuppressWarnings("unused")
    private PersonResponseDto() {
    }

    public PersonResponseDto(Person person) {
        this.username = person.getUsername();
        this.email = person.getEmail();
        this.password = person.getPassword();
        this.phone = person.getPhone();
        this.userId = person.getUserId();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserId(int id) {
        this.userId = id;
    }
}
