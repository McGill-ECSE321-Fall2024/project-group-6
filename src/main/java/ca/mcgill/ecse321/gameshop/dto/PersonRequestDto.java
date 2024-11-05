package ca.mcgill.ecse321.gameshop.dto;

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

    @SuppressWarnings("unused")
    private PersonRequestDto() {
    }

    public PersonRequestDto(String username, String email, String phone, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
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
