package ca.mcgill.ecse321.gameshop.dto;

import java.util.List;
public class RolesResponseDTO {
    private List<RoleResponseDTO> roles;

    public RolesResponseDTO(List<RoleResponseDTO> roles) {
        this.roles = roles;
    }

    public List<RoleResponseDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResponseDTO> roles) {
        this.roles = roles;
    }
}

