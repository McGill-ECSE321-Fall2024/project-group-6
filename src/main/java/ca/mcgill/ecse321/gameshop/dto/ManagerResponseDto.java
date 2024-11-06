package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.dto.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ca.mcgill.ecse321.gameshop.model.*;
public class ManagerResponseDto {

    private int managerId;
    private String username;

    private String email;

    private String phone;

    @SuppressWarnings("unused")
    private ManagerResponseDto() {
    }
    public ManagerResponseDto(Manager manager) {
        this.managerId = manager.getRoleId();
        this.username = manager.getPerson().getUsername();
        this.email = manager.getPerson().getEmail();
        this.phone = manager.getPerson().getPhone();


    }

    public int getManagerId() {
        return managerId;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }





}
