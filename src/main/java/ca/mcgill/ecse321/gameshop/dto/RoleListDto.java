package ca.mcgill.ecse321.gameshop.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ca.mcgill.ecse321.gameshop.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoleListDto {

    private List<RoleResponseDto> roles;

    public RoleListDto(List<RoleResponseDto> roles) {

        this.roles=roles;
    }

    public List<RoleResponseDto> getRoles() {

        return roles;
    }

    public void setRoles(List<RoleResponseDto> roles) {

        this.roles = roles;
    }
}
