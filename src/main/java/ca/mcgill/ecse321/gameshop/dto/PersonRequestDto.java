package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Person;

public class PersonRequestDto {
    //Person Attributes
    private String username;
    private String email;
    private String phone;
    private String password;

    public PersonRequestDto(Person person) {
        this.username = person.getUsername();
        this.email = person.getEmail();
        this.phone = person.getPhone();
        this.password = person.getPassword();
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

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
