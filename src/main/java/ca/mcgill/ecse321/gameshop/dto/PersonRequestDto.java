package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Person;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PersonRequestDto {
    //Person Attributes
    @NotBlank(message="Person name is required.")
    private String username;
    @NotBlank(message = "Email address is required.")
	@Email(message = "Invalid email address.")
    private String email;
    @NotBlank(message="A phone number is required.")
    private String phone;
    @NotBlank(message="A password is required.")
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
