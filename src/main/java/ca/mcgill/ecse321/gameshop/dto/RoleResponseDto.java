package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.*;
import jakarta.validation.constraints.NotBlank;

public class RoleResponseDto {
    //Person Attributes
    private Person person;
    @NotBlank(message="Id can not be null")
    private int roleId;
    public RoleResponseDto(Role role) {
        roleId=role.getRoleId();
        person=role.getPerson();

    }

    public Person getPerson() {
        return person;
    }
    public int getRoleId(){
        return roleId;
    }

    public void Person(Person person) {
        this.person= person;
    }
    public void setRoleId(int roleId){
        this.roleId=roleId;
    }
}