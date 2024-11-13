package ca.mcgill.ecse321.gameshop.dto;


import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmployeeRequestDto {

    @NotBlank(message="Manager name is required.")
    private String username;
    @NotBlank(message = "Email address is required.")
    @Email(message = "Invalid email address.")
    private String email;
    @NotBlank(message="A phone number is required.")
    private String phone;
    @NotBlank(message="A password is required.")
    private String password;

    private List <String> assignedTasks;

    @SuppressWarnings("unused")
    private EmployeeRequestDto() {
    }
    
    public EmployeeRequestDto(String username, String email, String phone, String password, List <String> tasks) {
        this.username=username;
        this.email=email;
        this.phone=phone;
        this.password=password;
        this.assignedTasks=tasks;

    }

    public List <String> getAssignedTasks() {

        return assignedTasks;
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

