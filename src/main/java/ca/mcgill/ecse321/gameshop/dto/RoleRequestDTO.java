package ca.mcgill.ecse321.gameshop.dto;

import ca.mcgill.ecse321.gameshop.model.Person;
import ca.mcgill.ecse321.gameshop.model.Role;
public class RoleRequestDTO {
    private Person person;

    public RoleRequestDTO() {
    }

    public RoleRequestDTO(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson (Person person) {
        this.person = person;
    }
}

