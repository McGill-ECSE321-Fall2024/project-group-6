package ca.mcgill.ecse321.gameshop.dto;


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
