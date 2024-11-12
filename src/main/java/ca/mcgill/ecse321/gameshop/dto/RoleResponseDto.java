package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.model.Role;
import jakarta.validation.constraints.NotBlank;

public class RoleResponseDto {
    //Person Attributes
    private final Person person;
    @NotBlank(message="Id can not be null")
    private final int roleId;
    
    public RoleResponseDto(Role role) {
        roleId = role.getRoleId();
        person = role.getPerson();
    }

    public Person getPerson() {
        return person;
    }

    public int getRoleId(){
        return roleId;
    }
}