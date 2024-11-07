package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.model.Role;

public class RoleResponseDTO {
    private int roleId;
    private Person person;

    public RoleResponseDTO() {
    }

    public RoleResponseDTO(Role role) {
        this.roleId = role.getRoleId();
        this.person = role.getPerson();
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson (Person person) {
        this.person = person;
    }
}

