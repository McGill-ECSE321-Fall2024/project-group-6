package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.dto.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ManagerRequestDto {

    @NotBlank(message="Manager name is required.")
    private String username;
    @NotBlank(message = "Email address is required.")
    @Email(message = "Invalid email address.")
    private String email;
    @NotBlank(message="A phone number is required.")
    private String phone;
    @NotBlank
    private String password;

    @SuppressWarnings("unused")
    private ManagerRequestDto() {
    }

    public ManagerRequestDto( String username, String email, String phone, String password) {
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


}
